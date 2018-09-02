package com.bdcoe.saksham.Dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bdcoe.saksham.R


/**
 * Created by rishi on 31/8/18.
 */
class MedalTallyDialog : android.support.v4.app.DialogFragment() {

    private lateinit var okButton: TextView
    private var mYear: Int = 2015

    companion object {

        fun newInstance(): MedalTallyDialog {
            return MedalTallyDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MedalTallyDialogStyle)
        val bundle = this.arguments
        if (bundle != null) {
            mYear = bundle.getInt("year", 2015)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(activity)
        var layout:Int = R.layout.medals_tally_2015
        when (mYear){
            2015 -> layout = R.layout.medals_tally_2015
            2016-> layout = R.layout.medals_tally_2016
            2017 -> layout = R.layout.medals_tally_2017
        }
        val view = inflater.inflate(layout, null)

        okButton = view?.findViewById<TextView>(R.id.medal_tally_dialog_ok)!!
        okButton.setOnClickListener {
            dialog.dismiss()
        }

        return AlertDialog.Builder(this.activity!!)
                .setView(view)
                .setCancelable(true)
                .create()


    }
}