package com.bdcoe.saksham.Dialogs

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bdcoe.saksham.Network.Clients.BdcoeClient
import com.bdcoe.saksham.Network.ServiceGenerator
import com.bdcoe.saksham.POJOs.Poll.PollResult
import com.bdcoe.saksham.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by rishi on 1/9/18.
 */
class PollDialog : android.support.v4.app.DialogFragment() {

    private lateinit var mSubmitButton: Button
    private lateinit var mCloseButton: Button
    private lateinit var mSpinner: Spinner
    private var mSelected: Boolean = false
    private val mTeamNames = ArrayList<String>()
    private lateinit var client: BdcoeClient

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

        client = ServiceGenerator.createBdcoeService(BdcoeClient::class.java)
        val sharedPreferences = context?.getSharedPreferences("Poll", Context.MODE_PRIVATE)
        val voted = sharedPreferences?.getBoolean("Voted", false)
        var layout = R.layout.dialog_vote
        if (voted!!) layout = R.layout.dialog_voted

        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(layout, null)

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
                editor.putBoolean("Voted", true)
                editor.putString("VotedFor", mSpinner.selectedItem.toString())
                editor.commit()


                postPollRequest(mSpinner.selectedItem.toString())

//                if (mSelected) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        Toast.makeText(this.context, mSpinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
//                    }
//                }
                dialog.dismiss()

            }

            mCloseButton.setOnClickListener {
                dialog.dismiss()
            }
        } else {
            val votedFor = sharedPreferences.getString("VotedFor", "CS")
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

    private fun postPollRequest(branch: String) {

        var dataflow: Int = 0
        when (branch) {
            "CS" -> dataflow = 5
            "IT" -> dataflow = 4
            "EC" -> dataflow = 2
            "ME" -> dataflow = 3
            "EN" -> dataflow = 1
            "CE / EI" -> dataflow = 6
            "MCA / MBA" -> dataflow = 7
        }
        val call = callPolls(dataflow.toString())
        call.enqueue(object : Callback<PollResult>{
            override fun onFailure(call: Call<PollResult>?, t: Throwable?) {
                Toast.makeText(context, "Polls Submit Failed", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<PollResult>?, response: Response<PollResult>?) {
//                Toast.makeText(context, "Polls Submited, Restart App to see updates.", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun callPolls(dataflow: String): Call<PollResult> = client.postPolls(dataflow)

}