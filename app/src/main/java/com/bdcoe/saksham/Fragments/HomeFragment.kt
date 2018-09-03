package com.bdcoe.saksham.Fragments


import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
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
import android.util.Log
import android.widget.*
import com.bdcoe.saksham.Adapters.NewsAdapter
import com.bdcoe.saksham.Dialogs.NewsDialog
import com.bdcoe.saksham.Dialogs.PollDialog
import com.bdcoe.saksham.Network.Clients.BdcoeClient
import com.bdcoe.saksham.Network.ServiceGenerator
import com.bdcoe.saksham.POJOs.Medals.MedalsResult
import com.bdcoe.saksham.POJOs.News.NewsResult
import com.bdcoe.saksham.POJOs.Poll.PollResult
import com.github.marlonlom.utilities.timeago.TimeAgo
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var colorChangeHandler: Handler
    private lateinit var colorChangingRunnable: Runnable
    private var fragmentRunning: Boolean = false
    private lateinit var pieChart:PieChart

    private var newsList: ArrayList<com.bdcoe.saksham.POJOs.News.List> = ArrayList<com.bdcoe.saksham.POJOs.News.List>()
    private lateinit var viewAdapter: NewsAdapter
    private lateinit var viewManager: LinearLayoutManager


    private lateinit var client:BdcoeClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentRunning = true
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        client = ServiceGenerator.createBdcoeService(BdcoeClient::class.java)

        viewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewAdapter = object : NewsAdapter(context!!, newsList) {
            override fun showDialog(news: com.bdcoe.saksham.POJOs.News.List) {

                val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
                val dialogFragment = NewsDialog()
                val bundle = Bundle()

                val images: ArrayList<String> = ArrayList()
                if (news.image1 != null )   images.add(news.image1)
                if (news.image2 != null )   images.add(news.image2)
                if (news.image3 != null )   images.add(news.image3)
                if (news.image4 != null )   images.add(news.image4)

                bundle.putString("NewsTeams", news.teams)
                bundle.putString("NewsTitle", news.sport.trim())
                bundle.putString("NewsDescription", news.desc)

                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = sdf.parse(news.timestamp)
                val millis = date.time
                val relativeTime = TimeAgo.using(millis)

                bundle.putString("Timestamp", relativeTime)
                bundle.putStringArrayList("Images", images)

                dialogFragment.arguments = bundle
                dialogFragment.show(ft, "dialog")

            }
        }

        latest_news_recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            itemAnimator = DefaultItemAnimator()
        }



        loadMedalTally()
        loadPolls()
        loadNews()

        pieChart = view.findViewById<PieChart>(R.id.poll_chart)
//        initializePollChart(pieChart)
        initializeHeaderAnimation()
        initializeClickListners()





    }

    private fun initializePollChart(pieChart: PieChart,pollsArray:ArrayList<Float>) {

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
        data.dataSet = dataSet
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
                        trans.startTransition(3000)
                    }
                    if (nextPosition == 3) nextPosition = 0
                    if (previousPosition == 3) previousPosition = 0
                    colorChangeHandler.postDelayed(this, 3000)
                }
            }
        }
        colorChangeHandler.post(colorChangingRunnable)
    }

    private fun initializeClickListners() {
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


    private fun loadMedalTally() {
        val call = callMedals()
        call.enqueue(object : Callback<MedalsResult>{
            override fun onFailure(call: Call<MedalsResult>?, t: Throwable?) {
                Toast.makeText(context, "Medals Load Failed", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MedalsResult>?, response: Response<MedalsResult>?) {
                val medalsData = response?.body()
                if (medalsData != null && medalsData.result.toInt() == 1) {
                    populateMedalsInTable(medalsData?.list)
                } else Toast.makeText(context,"No Data Found",Toast.LENGTH_SHORT).show()
            }

            private fun populateMedalsInTable(list: List<com.bdcoe.saksham.POJOs.Medals.List>?) {
                if(medal_row_placeholder != null)   medal_row_placeholder.visibility = View.GONE
                list?.forEach {
                    val inflater = LayoutInflater.from(context)
                    val view = inflater.inflate(R.layout.medal_tally_row,null)
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
                }

            }

        })
    }

    private fun loadPolls() {
        val call = callPolls()
        call.enqueue(object : Callback<PollResult>{
            override fun onFailure(call: Call<PollResult>?, t: Throwable?) {
                Toast.makeText(context, "Polls Load Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PollResult>?, response: Response<PollResult>?) {
                val data = response?.body()
                val pollsArray = ArrayList<Float>()
                if (data?.result?.toInt() == 1){
                    pollsArray.add(data.list[0].cs.toFloat())
                    pollsArray.add(data.list[0].it.toFloat())
                    pollsArray.add(data.list[0].ec.toFloat())
                    pollsArray.add(data.list[0].me.toFloat())
                    pollsArray.add(data.list[0].en.toFloat())
                    pollsArray.add(data.list[0].ceei.toFloat())
                    pollsArray.add(data.list[0].mca.toFloat())
                }
                initializePollChart(pieChart,pollsArray)
                total_responses.text = data?.list!![0].total.toString() + " Responses so far"
            }
        })

    }

    private fun loadNews() {
        val call = callNews()
        call.enqueue(object : Callback<NewsResult> {
            override fun onFailure(call: Call<NewsResult>?, t: Throwable?) {
                if (news_swipe_refresh != null) news_swipe_refresh.isRefreshing = false
                Toast.makeText(context, "Load News Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewsResult>?, response: Response<NewsResult>?) {
                newsList.removeAll(newsList)
                if (news_swipe_refresh != null) news_swipe_refresh.isRefreshing = false
                val data = response?.body()

                if (data?.list != null){
                    newsList.add(data.list[0])
                    newsList.add(data.list[1])
                    viewAdapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(context,"Latest News Load Failed",Toast.LENGTH_SHORT).show()
                }

            }

        })
    }

    private fun callMedals(): Call<MedalsResult> = client.getMedalas("3")
    private fun callPolls(): Call<PollResult> = client.getPolls("0")
    private fun callNews(): Call<NewsResult> = client.getNews("2")


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
