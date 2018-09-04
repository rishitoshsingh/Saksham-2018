package com.bdcoe.saksham.Fragments


import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bdcoe.saksham.R
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.fragment_about_us.*


/**
 * A simple [Fragment] subclass.
 */
class AboutUsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* button1.setOnClickListener {

            val phoneNumber = "8448161159"
            val callintent = Intent(Intent.ACTION_CALL)
            callintent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callintent)
        }*/

    }

    /*fun call(view:View){
        val phoneNumber = "8448161159"
        val callintent = Intent(Intent.ACTION_CALL)
        callintent.setData(Uri.parse("tel:$phoneNumber"))
        startActivity(callintent)
    }*/


}// Required empty public constructor
