package com.bdcoe.saksham.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bdcoe.saksham.Dialogs.MedalTallyDialog
import com.bdcoe.saksham.Dialogs.NewsDialog

import com.bdcoe.saksham.R
import kotlinx.android.synthetic.main.fragment_news.*


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        news_template.setOnClickListener {
//
//            val ft: android.support.v4.app.FragmentTransaction = fragmentManager!!.beginTransaction()
//            val dialogFragment = NewsDialog()
//            val bundle = Bundle()
//
//            val images = ArrayList<String>()
//            images.add("285/one.jpg")
//            images.add("351/one.jpg")
//
//            bundle.putString("NewsTeams", "CS vs ME")
//            bundle.putString("NewsTitle", "Title")
//            bundle.putString("NewsDescription", resources.getString(R.string.large_text))
//            bundle.putString("Timestamp", "2 Days ago")
//            bundle.putStringArrayList("Images",images)
//
//            dialogFragment.arguments = bundle
//            dialogFragment.show(ft, "dialog")
//
//        }


    }
}// Required empty public constructor
