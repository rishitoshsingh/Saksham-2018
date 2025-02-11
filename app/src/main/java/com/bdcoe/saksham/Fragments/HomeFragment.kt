package com.bdcoe.saksham.Fragments


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bdcoe.saksham.Dialogs.MedalTallyDialog
import com.bdcoe.saksham.Dialogs.PollDialog
import com.bdcoe.saksham.Network.Clients.BdcoeClient
import com.bdcoe.saksham.Network.ServiceGenerator
import com.bdcoe.saksham.POJOs.Medals.MedalsResult
import com.bdcoe.saksham.POJOs.Poll.PollResult
import com.bdcoe.saksham.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var colorChangeHandler: Handler
    private lateinit var colorChangingRunnable: Runnable
    private var fragmentRunning: Boolean = false

    private val POLL_DIALOG_REQUEST_CODE: Int = 90
    private val POLL_SUBMITTED_CODE: Int = 1
    private val POLL_NOT_SUBMITTED_CODE: Int = 0

    private lateinit var client: BdcoeClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentRunning = true
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        client = ServiceGenerator.createBdcoeService(BdcoeClient::class.java)

        loadMedalTally()
        loadPolls()

//        initializePollChart(pieChart)
        initializeHeaderAnimation()
        initializeClickListners()


    }

    private fun initializePollChart(pieChart: PieChart, pollsArray: ArrayList<Float>) {

        var i = 0
        val entries = mutableListOf<PieEntry>()
        pollsArray.forEach {
            entries.add(PieEntry(it, i++))
        }
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
        data.dataSet = dataSet as IPieDataSet?
        data.setValueFormatter(PercentFormatter())
        pieChart.data = data

        dataSet.colors = teamColorsList
        pieChart.animateY(500)
        pieChart.setUsePercentValues(true)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.WHITE)

    }

    private fun initializeHeaderAnimation() {
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
                        trans.startTransition(2000)
                    }
                    if (nextPosition == 3) nextPosition = 0
                    if (previousPosition == 3) previousPosition = 0
                    colorChangeHandler.postDelayed(this, 5000)
                }
            }
        }
        colorChangeHandler.post(colorChangingRunnable)
    }

    private fun initializeClickListners() {
        show_2016.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = MedalTallyDialog()
            val bundle = Bundle()
            bundle.putInt("year", 2016)
            dialogFragment.arguments = bundle
            dialogFragment.show(ft, "dialog")
        }
        show_2017.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = MedalTallyDialog()
            val bundle = Bundle()
            bundle.putInt("year", 2017)
            dialogFragment.arguments = bundle
            dialogFragment.show(ft, "dialog")
        }
        show_2018.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = MedalTallyDialog()
            val bundle = Bundle()
            bundle.putInt("year", 2018)
            dialogFragment.arguments = bundle
            dialogFragment.show(ft, "dialog")
        }
        vote_button.setOnClickListener {
            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
            val dialogFragment = PollDialog()
            dialogFragment.setTargetFragment(this, POLL_DIALOG_REQUEST_CODE)
            dialogFragment.show(ft, "dialog")


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == POLL_DIALOG_REQUEST_CODE) {
            if (resultCode == POLL_SUBMITTED_CODE) {
                loadPolls()
            }
        }

    }

    private fun loadMedalTally() {
        val call = callMedals()
        call.enqueue(object : Callback<MedalsResult> {
            override fun onFailure(call: Call<MedalsResult>?, t: Throwable?) {
                if (medals_progressbar != null) medals_progressbar.visibility = View.GONE
                if (medals_error_view != null) medals_error_view.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<MedalsResult>?, response: Response<MedalsResult>?) {
                try {
                    val medalsData = response?.body()
                    if (medalsData != null && medalsData.result.toInt() == 1) {
                        medals_empty_view.visibility = View.GONE
                        populateMedalsInTable(medalsData?.list)
                    } else {
                        if (medals_progressbar != null) medals_progressbar.visibility = View.GONE
                        if (medals_error_view != null) medals_error_view.visibility = View.VISIBLE
                    }
                } catch (ex: Exception) {
                    if (medals_progressbar != null) medals_progressbar.visibility = View.GONE
                    if (medals_error_view != null) medals_error_view.visibility = View.VISIBLE
                    Log.d("MedalLoadFailed", ex.toString())
                }
            }

            private fun populateMedalsInTable(list: List<com.bdcoe.saksham.POJOs.Medals.List>?) {
                if (medal_row_placeholder != null) medal_row_placeholder.visibility = View.GONE
                list?.forEach {
                    try {
                        val inflater = LayoutInflater.from(context)
                        val view = inflater.inflate(R.layout.medal_tally_row, null)
                        val teamTextView = view.findViewById<TextView>(R.id.row_team)
                        val goldTextView = view.findViewById<TextView>(R.id.row_gold)
                        val silverTextView = view.findViewById<TextView>(R.id.row_silver)
                        val bronzeTextView = view.findViewById<TextView>(R.id.row_bronze)
                        val totalTextView = view.findViewById<TextView>(R.id.row_total)

                        teamTextView.text = it.branch
                        goldTextView.text = it.gold
                        silverTextView.text = it.silver
                        bronzeTextView.text = it.bronze
                        totalTextView.text = it.total

                        medal_tally_linear_root.addView(view)
                    } catch (ex:Exception){
                        Log.d("MedalLoadFailed", ex.toString())
                    }
                }

            }

        })
    }

    private fun loadPolls() {
        val call = callPolls()
        call.enqueue(object : Callback<PollResult> {
            override fun onFailure(call: Call<PollResult>?, t: Throwable?) {
                Snackbar.make(home_root,"Polls Load Failed", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PollResult>?, response: Response<PollResult>?) {
                try {
                    val data = response?.body()
                    val pollsArray = ArrayList<Float>()
                    if (data?.result?.toInt() == 1) {
                        pollsArray.add(data.list[0].cs.toFloat())
                        pollsArray.add(data.list[0].it.toFloat())
                        pollsArray.add(data.list[0].ec.toFloat())
                        pollsArray.add(data.list[0].me.toFloat())
                        pollsArray.add(data.list[0].en.toFloat())
                        pollsArray.add(data.list[0].ceei.toFloat())
                        pollsArray.add(data.list[0].mca.toFloat())
                    }
                    val pieChart: PieChart = view?.findViewById<PieChart>(R.id.poll_chart)!!


                    initializePollChart(pieChart, pollsArray)
                    total_responses.text = data?.list!![0].total.toString() + " Responses so far"
                } catch (ex: Exception) {
                    Log.d("PollsLoadError", ex.toString())
                }
            }
        })

    }

    private fun callMedals(): Call<MedalsResult> = client.getMedalas("3")
    private fun callPolls(): Call<PollResult> = client.getPolls("0")


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

}