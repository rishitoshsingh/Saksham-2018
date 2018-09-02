package com.bdcoe.saksham.Dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bdcoe.saksham.R


/**
 * Created by rishi on 1/9/18.
 */
class PollDialog : android.support.v4.app.DialogFragment() {

    private lateinit var mSubmitButton: Button
    private lateinit var mCloseButton: Button
    private lateinit var mSpinner: Spinner
    private var mSelected: Boolean = false
    private val mTeamNames = ArrayList<String>()

    companion object {
        fun newInstance(): PollDialog {
            return PollDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, R.style.MedalTallyDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val sharedPreferences = context?.getSharedPreferences("Poll",Context.MODE_PRIVATE)
        val voted = sharedPreferences?.getBoolean("Voted",false)
        var layout = R.layout.dialog_vote
        if (voted!!) layout = R.layout.dialog_voted

        val inflater = LayoutInflater.from(activity)
        val  view = inflater.inflate(layout, null)

        if (!voted) {
            mSubmitButton = view?.findViewById<Button>(R.id.poll_dialog_submit)!!
            mCloseButton = view.findViewById<Button>(R.id.poll_dialog_close)
            mSpinner = view.findViewById<Spinner>(R.id.poll_spinner)
            mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    mSelected = false
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mSelected = true
                    mSubmitButton.isEnabled = true
                }
            }

            resources.getStringArray(R.array.team_names).toCollection(mTeamNames)
            val dataAdapter = ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item, mTeamNames)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinner.adapter = dataAdapter
            mSubmitButton.isEnabled = false

            mSubmitButton.setOnClickListener {

                val editor = sharedPreferences.edit()
                editor.putBoolean("Voted",true)
                editor.putString("VotedFor",mSpinner.selectedItem.toString())
                editor.commit()

                if (mSelected) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(this.context, mSpinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                dialog.dismiss()

            }

            mCloseButton.setOnClickListener {
                dialog.dismiss()
            }
        } else {
            val votedFor = sharedPreferences.getString("VotedFor","CS")
            view.findViewById<TextView>(R.id.voted_for).text = votedFor
            mCloseButton = view.findViewById<Button>(R.id.voted_for_close_button)

            mCloseButton.setOnClickListener {
                dialog.dismiss()
            }
        }

        return AlertDialog.Builder(this.activity!!)
                .setView(view)
                .setCancelable(true)
                .create()

    }
}