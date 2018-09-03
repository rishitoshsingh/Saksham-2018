package com.bdcoe.saksham.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bdcoe.saksham.Dialogs.MedalTallyDialog

import com.bdcoe.saksham.R
import kotlinx.android.synthetic.main.fragment_schedule.*


/**
 * A simple [Fragment] subclass.
 */
class ScheduleFragment : Fragment() {

    private lateinit var mSports: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cricket_schedule.setOnClickListener {
            mSports = "Cricket"
            showDialog()
        }
        basketball_schedule.setOnClickListener {
            mSports = "Basketball"
            showDialog()
        }
        football_schedule.setOnClickListener {
            mSports = "Football"
            showDialog()
        }
        table_tennis_schedule.setOnClickListener {
            mSports = "Table-Tennis"
            showDialog()
        }
        atheletics_schedule.setOnClickListener {
            mSports = "Athletics"
            showDialog()
        }
        badminton_schedule.setOnClickListener {
            mSports = "Badminton"
            showDialog()
        }
        powerlifting_schedule.setOnClickListener {
            mSports = "Powerlifting"
            showDialog()
        }
        carrom_schedule.setOnClickListener {
            mSports = "Carrom"
            showDialog()
        }
        chess_schedule.setOnClickListener {
            mSports = "Chess"
            showDialog()
        }
        tug_of_war_schedule.setOnClickListener {
            mSports = "Tug of War"
            showDialog()
        }
        kabaddi_schedule.setOnClickListener {
            mSports = "Kabaddi"
            showDialog()
        }
        pool_schedule.setOnClickListener {
            mSports = "Pool"
            showDialog()
        }
        kho_kho_schedule.setOnClickListener {
            mSports = "Kho-Kho"
            showDialog()
        }
        volleyball_schedule.setOnClickListener {
            mSports = "Volleyball"
            showDialog()
        }
        obstacle_race_schedule.setOnClickListener {
            mSports = "Obstucle Race"
            showDialog()
        }
    }

    private fun showDialog(){
        val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
        val bottomSheetFragment = BottomSheetFragment()
        val bundle = Bundle()
        bundle.putString("Sports",mSports)
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show(ft, "bottomSheet")
    }

}