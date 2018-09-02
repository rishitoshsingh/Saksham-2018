package com.bdcoe.saksham.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatSpinner
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bdcoe.saksham.Adapters.SlidingImageAdapter
import com.bdcoe.saksham.R
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import org.w3c.dom.Text
import java.util.zip.Inflater

/**
 * Created by rishi on 1/9/18.
 */
class NewsDialog:DialogFragment() {

    private lateinit var mCloseButton: Button
    private lateinit var mTeamsTextView: TextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mTimestampTextView: TextView
    private lateinit var mDescriptionTextView: TextView
    private lateinit var mImagesPager:ViewPager
    private lateinit var mImagePagerIndicator:IndefinitePagerIndicator

    private lateinit var mTeams:String
    private lateinit var mNewsTitle:String
    private lateinit var mNewsDescription:String
    private lateinit var mTimestamp:String
    private lateinit var mImages:ArrayList<String>

    companion object {
        fun newInstance(): NewsDialog {
            return NewsDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.MedalTallyDialogStyle)

        val bundle = this.arguments
        if (bundle != null) {
            mTeams = bundle.getString("NewsTeams", "CS vs ME")
            mNewsTitle = bundle.getString("NewsTitle", "Title")
            mNewsDescription = bundle.getString("NewsDescription", resources.getString(R.string.large_text))
            mTimestamp = bundle.getString("Timestamp", "2 Days ago")
            mImages = bundle.getStringArrayList("Images")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.dialog_news,null)
        mImagesPager = view.findViewById<ViewPager>(R.id.news_dialog_images_viewpager)
        mImagePagerIndicator = view.findViewById<IndefinitePagerIndicator>(R.id.news_dialog_images_page_indicator)
        mTeamsTextView = view.findViewById<TextView>(R.id.news_dialog_teams)
        mTitleTextView = view.findViewById<TextView>(R.id.news_dialog_title)
        mDescriptionTextView = view.findViewById<TextView>(R.id.news_dialog_description)
        mTimestampTextView = view.findViewById<TextView>(R.id.news_dialog_timestamp)
        mCloseButton = view.findViewById<Button>(R.id.news_dialog_close)


        mTeamsTextView.text = mTeams
        mTimestampTextView.text = mTimestamp
        mTitleTextView.text = mNewsTitle
        mDescriptionTextView.text = mNewsDescription
        mImagesPager.adapter = SlidingImageAdapter(this.activity!!, mImages)
        mImagePagerIndicator.attachToViewPager(mImagesPager)
        mCloseButton.setOnClickListener {
            dialog.dismiss()
        }

        return AlertDialog.Builder(this.activity!!)
                .setView(view)
                .setCancelable(true)
                .create()
    }
}


