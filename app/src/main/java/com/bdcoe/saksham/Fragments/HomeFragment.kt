package com.bdcoe.saksham.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bdcoe.saksham.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import android.R.attr.entries
import android.graphics.Color
import com.bdcoe.saksham.R.id.poll_chart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pieChart = view.findViewById<PieChart>(R.id.poll_chart)
        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(4f, 0))
        entries.add(PieEntry(8f, 1))
        entries.add(PieEntry(6f, 2))
        entries.add(PieEntry(12f, 3))
        entries.add(PieEntry(18f, 4))
        entries.add(PieEntry(9f, 5))
        val dataset = PieDataSet(entries, "# of Calls")

        val pieLabels = ArrayList<String>()
        pieLabels.add("January")
        pieLabels.add("February")
        pieLabels.add("March")
        pieLabels.add("April")
        pieLabels.add("May")
        pieLabels.add("June")


// creating labels
        val labels = ArrayList<String>()
        labels.add("January")
        labels.add("February")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")

        val data = PieData(dataset) // initialize Piedata
        pieChart.data = data
        val colorList = ArrayList<Int>()
        colorList.add(Color.RED)
        colorList.add(Color.BLACK)
        colorList.add(Color.GREEN)
        colorList.add(Color.CYAN)
        colorList.add(Color.BLUE)
        colorList.add(resources.getColor(R.color.purple))
        dataset.colors = colorList
//        pieChart.animateY(5000)
        pieChart.animateX(500)

    }
}// Required empty public constructor
