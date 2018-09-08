package com.bdcoe.saksham.Fragments


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bdcoe.saksham.Adapters.NewsAdapter
import com.bdcoe.saksham.Dialogs.NewsDialog
import com.bdcoe.saksham.Network.Clients.BdcoeClient
import com.bdcoe.saksham.Network.ServiceGenerator
import com.bdcoe.saksham.POJOs.News.List
import com.bdcoe.saksham.POJOs.News.NewsResult
import com.bdcoe.saksham.R
import com.github.marlonlom.utilities.timeago.TimeAgo
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private var newsList: ArrayList<List> = ArrayList<List>()
    private lateinit var viewAdapter: NewsAdapter
    private lateinit var viewManager: LinearLayoutManager

    private lateinit var client: BdcoeClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        client = ServiceGenerator.createBdcoeService(BdcoeClient::class.java)

        viewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewAdapter = object : NewsAdapter(context!!, newsList) {
            override fun showDialog(news: List) {

                val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
                val dialogFragment = NewsDialog()
                val bundle = Bundle()

                val images: ArrayList<String> = ArrayList()
                if (news.image1 != null )   images.add(news.image1)
                if (news.image2 != null )   images.add(news.image2)
                if (news.image3 != null )   images.add(news.image3)
                if (news.image4 != null )   images.add(news.image4)


                bundle.putString("NewsDescription", news.desc)
                if (news.sport.isEmpty()){
                    bundle.putInt("Icon",R.drawable.bdc_white)
                    bundle.putString("NewsTeams", "")
                    bundle.putString("NewsTitle", "Welcome")
                } else {
                    bundle.putInt("Icon",getSportsDrawable(news.sport.trim()))
                    bundle.putString("NewsTeams", news.teams)
                    bundle.putString("NewsTitle", news.sport.trim())
                }

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
        news_recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            itemAnimator = DefaultItemAnimator()
        }

        if (news_swipe_refresh != null) news_swipe_refresh.isRefreshing = true

        news_swipe_refresh.setOnRefreshListener {
            loadNews()
            if (news_swipe_refresh != null) news_swipe_refresh.isRefreshing = false
        }
        loadNews()
    }

    private fun loadNews() {
        val call = callNews()
        call.enqueue(object : Callback<NewsResult> {
            override fun onFailure(call: Call<NewsResult>?, t: Throwable?) {
                if (news_swipe_refresh != null) news_swipe_refresh.isRefreshing = false
                Snackbar.make(news_swipe_refresh,"News Load Failed", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<NewsResult>?, response: Response<NewsResult>?) {
                try {
                    newsList.removeAll(newsList)
                    if (news_swipe_refresh != null) news_swipe_refresh.isRefreshing = false
                    val data = response?.body()
                    if (data?.result?.toInt() == 1){
                        if (!data.list.isEmpty()){
                            data.list?.forEach {
                                newsList.add(it)
                            }
                            viewAdapter.notifyDataSetChanged()
                        }
                    }
                } catch (ex:Exception) {
                    Log.d("NewsLoadFailed",ex.toString())
                }
            }

        })


    }

    private fun getSportsDrawable(sport: String): Int {
        return when (sport) {
            "Athletics" -> R.drawable.athletics
            "Football" -> R.drawable.football
            "Cricket" -> R.drawable.cricket
            "Basketball" -> R.drawable.basketball
            "Kabaddi" -> R.drawable.kabaddi
            "Table Tennis" -> R.drawable.table_tennis
            "Kho Kho" -> R.drawable.kho_kho
            "Badminton" -> R.drawable.badminton
            "Carrom" -> R.drawable.carrom
            "Chess" -> R.drawable.chess
            "Powerlifting" -> R.drawable.powerlifting
            "Pool" -> R.drawable.pool
            "Tug of War" -> R.drawable.tug_of_war
            "Volleyball" -> R.drawable.volleyball
            "Obstacle Race" -> R.drawable.hurdles
            else -> R.drawable.athletics
        }
    }

    private fun callNews(): Call<NewsResult> = client.getNews("3")


}