package com.bdcoe.saksham.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bdcoe.saksham.POJOs.News.List
import com.bdcoe.saksham.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.text.SimpleDateFormat


/**
 * Created by rishi on 2/9/18.
 */

abstract class NewsAdapter(context: Context, moviesPassed: ArrayList<List>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    var newsArrayList: ArrayList<List> = moviesPassed

    private val IMAGE_BASE_URL = "http://saksham2015.pe.hu/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val newsView = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return NewsMovieHolder(newsView)
    }

    override fun getItemCount() = newsArrayList.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val newsViewHolder = holder as NewsMovieHolder
        val news = newsArrayList[position]

        newsViewHolder.newsDescription.text = news.desc
        newsViewHolder.newsTeamVersus.text = news.teams
        newsViewHolder.newsSports.text = news.sport

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.parse(news.timestamp)
        val millis = date.time
        val relativeTime = TimeAgo.using(millis)

        newsViewHolder.newsTimestamp.text = relativeTime

        val imageUri = Uri.parse(IMAGE_BASE_URL + news.image1)

        Glide.with(mContext)
                .load(imageUri)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        newsViewHolder.newsProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        newsViewHolder.newsProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .apply(RequestOptions()
                        .error(R.drawable.header_placeholder)
                        .centerCrop())
                .into(newsViewHolder.newsImage)

     newsViewHolder.newsCardChild.setOnClickListener { showDialog(news) }
    }

    abstract fun showDialog(news: List)

    inner class NewsMovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        var newsDescription: TextView = view.findViewById<TextView>(R.id.news_description)
        var newsIcon: ImageView = view.findViewById<ImageView>(R.id.news_sports_icon)
        var newsTeamVersus: TextView = view.findViewById<TextView>(R.id.news_team_versus)
        var newsTimestamp: TextView = view.findViewById<TextView>(R.id.news_time_stamp)
        var newsSports: TextView = view.findViewById<TextView>(R.id.news_sports)
        var newsImage: ImageView = view.findViewById<ImageView>(R.id.news_image)
        var newsProgressBar: ProgressBar = view.findViewById<ProgressBar>(R.id.news_card_progressbar)
        var newsCardChild:RelativeLayout = view.findViewById<RelativeLayout>(R.id.news_card_child)
    }
}