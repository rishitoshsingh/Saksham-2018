package com.bdcoe.saksham.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bdcoe.saksham.Network.Clients.BdcoeClient
import com.bdcoe.saksham.Network.ServiceGenerator
import com.bdcoe.saksham.POJOs.Schedule.List
import com.bdcoe.saksham.POJOs.Schedule.ScheduleResult
import com.bdcoe.saksham.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by rishi on 3/9/18.
 */

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var client: BdcoeClient
    private lateinit var mSports: String
    private var mBoysSchedule: ArrayList<List> = ArrayList()
    private var mGirlsSchedule: ArrayList<List> = ArrayList()
    private lateinit var mInflater: LayoutInflater

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            mSports = bundle.getString("Sports")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mInflater = inflater
        return inflater.inflate(R.layout.schedule_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = ServiceGenerator.createBdcoeService(BdcoeClient::class.java)
        view.findViewById<TextView>(R.id.schedule_sports).text = mSports
        view.findViewById<Button>(R.id.schedule_close).setOnClickListener {
            dialog.dismiss()
        }
        networkCall(view)
    }

    private fun networkCall(view: View) {

        val call = callSchedule(mSports)
        call.enqueue(object : Callback<ScheduleResult> {
            override fun onFailure(call: Call<ScheduleResult>?, t: Throwable?) {
                view.findViewById<ProgressBar>(R.id.schedule_progressbar).visibility = View.GONE
                view.findViewById<TextView>(R.id.schedule_error_view).text = "Error,  Try Later"
                view.findViewById<TextView>(R.id.schedule_error_view).visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<ScheduleResult>?, response: Response<ScheduleResult>?) {
                view.findViewById<ProgressBar>(R.id.schedule_progressbar).visibility = View.GONE
                val data = response?.body()
                if (data != null && data.result.toInt() == 1) {
                    if (data.list.size != 0) {
                        data.list.forEach {
                            if (it.sport.contains("Girls")) mGirlsSchedule.add(it)
                            else mBoysSchedule.add(it)
                        }
                        if (mBoysSchedule.size != 0) addBoysSchedule(view)
                        if (mGirlsSchedule.size != 0) addGirlsSchedule(view)
                        view.findViewById<RelativeLayout>(R.id.schedule_empty_view).visibility = View.GONE
                    } else {
                        view.findViewById<TextView>(R.id.schedule_error_view).visibility = View.VISIBLE
                    }
                } else {
                    view.findViewById<TextView>(R.id.schedule_error_view).visibility = View.VISIBLE
                }
            }

        })


    }

    private fun addBoysSchedule(view: View) {
        view.findViewById<TextView>(R.id.schedule_boys_header).visibility = View.VISIBLE
        mBoysSchedule.forEach {
            val row = mInflater.inflate(R.layout.schedule_row, null)
            row.findViewById<TextView>(R.id.schedule_team).text = it.teams
            row.findViewById<TextView>(R.id.schedule_date).text = it.date
            row.findViewById<TextView>(R.id.schedule_time).text = it.time
            view.findViewById<LinearLayout>(R.id.schedule_boys).addView(row)
        }
    }

    private fun addGirlsSchedule(view: View) {
        view.findViewById<TextView>(R.id.schedule_girls_header).visibility = View.VISIBLE
        mGirlsSchedule.forEach {
            val row = mInflater.inflate(R.layout.schedule_row, null)
            row.findViewById<TextView>(R.id.schedule_team).text = it.teams
            row.findViewById<TextView>(R.id.schedule_date).text = it.date
            row.findViewById<TextView>(R.id.schedule_time).text = it.time
            view.findViewById<LinearLayout>(R.id.schedule_girls).addView(row)
        }
    }

    fun callSchedule(sports: String): Call<ScheduleResult> = client.getSchedule("3", sports)

}