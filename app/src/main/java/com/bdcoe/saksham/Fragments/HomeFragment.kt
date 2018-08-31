package com.bdcoe.saksham.Fragments


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bdcoe.saksham.Dialogs.MedalTallyDialog
import com.bdcoe.saksham.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.medals_tally_2015.*
import android.R.attr.fragment
import android.R.attr.key
import com.bdcoe.saksham.Dialogs.PollDialog


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var colorChangeHandler: Handler
    private lateinit var colorChangingRunnable: Runnable
    private var fragmentRunning: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentRunning = true
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pieChart = view.findViewById<PieChart>(R.id.poll_chart)
        initializePollChart(pieChart)


        val gradients = arrayOf(resources.getDrawable(R.drawable.pacific_dream), resources.getDrawable(R.drawable.venice), resources.getDrawable(R.drawable.can_you_feel_the_love_tonight), resources.getDrawable(R.drawable.the_blue_lagoon))
        var nextPosition = 1
        var previousPosition = 0
        colorChangeHandler = Handler()
        colorChangingRunnable = object : Runnable {
            override fun run() {
                if (fragmentRunning) {
                    val color = arrayOf(gradients[previousPosition++], gradients[nextPosition++])
                    val trans = TransitionDrawable(color)
                    activity?.runOnUiThread {
                        header_background.background = trans
                        trans.startTransition(3000)
                    }
                    if (nextPosition == 3) nextPosition = 0
                    if (previousPosition == 3) previousPosition = 0
                    colorChangeHandler.postDelayed(this, 3000)
                }
            }
        }
        colorChangeHandler.post(colorChangingRunnable)


        show_2015.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = MedalTallyDialog()
            val bundle = Bundle()
            bundle.putInt("year",2015)
            dialogFragment.arguments = bundle
            dialogFragment.show(ft, "dialog")
        }
        show_2016.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = MedalTallyDialog()
            val bundle = Bundle()
            bundle.putInt("year",2016)
            dialogFragment.arguments = bundle
            dialogFragment.show(ft, "dialog")
        }
        show_2017.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = MedalTallyDialog()
            val bundle = Bundle()
            bundle.putInt("year",2017)
            dialogFragment.arguments = bundle
            dialogFragment.show(ft, "dialog")
        }
        vote_button.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = PollDialog()
            dialogFragment.show(ft, "dialog")
        }

    }

    private fun initializePollChart(pieChart: PieChart) {

        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(4f, 0))
        entries.add(PieEntry(8f, 1))
        entries.add(PieEntry(6f, 2))
        entries.add(PieEntry(12f, 3))
        entries.add(PieEntry(18f, 4))
        entries.add(PieEntry(9f, 5))
        entries.add(PieEntry(9f, 5))
        val dataSet = PieDataSet(entries, "Win Poll")

        val labels = ArrayList<String>()
        labels.add("CS")
        labels.add("IT")
        labels.add("EC")
        labels.add("ME")
        labels.add("EN")
        labels.add("CE/EI")
        labels.add("MBA/MCA")
        val teamColorsArray = resources.getStringArray(R.array.team_colors)
        val teamColorsList = ArrayList<Int>()
        teamColorsArray.forEach {
            val color = Color.parseColor(it)
            teamColorsList.add(color)
        }
        val entriesData = ArrayList<LegendEntry>()
        for (i in 0 until labels.size) {
            val entry = LegendEntry()
            entry.formColor = teamColorsList[i]
            entry.label = labels[i]
            entriesData.add(entry)
        }

        pieChart.legend.setCustom(entriesData)
        pieChart.legend.form = Legend.LegendForm.CIRCLE
        pieChart.legend.direction = Legend.LegendDirection.RIGHT_TO_LEFT
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        pieChart.centerText = "Poll Result"
        pieChart.setCenterTextSize(22f)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pieChart.setCenterTextTypeface(resources.getFont(R.font.roboto_light))
        }
        pieChart.setCenterTextColor(resources.getColor(R.color.secondary_text))
        pieChart.legend.formSize = 13f
        val description = Description()
        description.text = ""
        pieChart.description = description
        val data = PieData() // initialize Piedata
        data.dataSet = dataSet
        data.setValueFormatter(PercentFormatter())
        pieChart.data = data

        dataSet.colors = teamColorsList
        pieChart.animateY(500)
        pieChart.setUsePercentValues(true)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.WHITE)

    }

    override fun onPause() {
        super.onPause()
        fragmentRunning = false
    }

    override fun onStart() {
        super.onStart()
        fragmentRunning = true
    }

    override fun onStop() {
        super.onStop()
        fragmentRunning = false
    }

    override fun onResume() {
        super.onResume()
        fragmentRunning = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentRunning = false
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRunning = false
    }

}// Required empty public constructor
