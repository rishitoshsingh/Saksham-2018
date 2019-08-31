package com.bdcoe.saksham.Fragments


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bdcoe.saksham.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_about_us.*

/**
 * A simple [Fragment] subclass.
 */
class AboutUsFragment : Fragment() {

    val BASE_URL:String = "http://bdcoesport.000webhostapp.com/uploads/organizers/"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(Uri.parse(BASE_URL + "prashar.webp")).into(parashar_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "sabu.webp")).into(sabu_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "ishit.webp")).into(ishit_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "utkarsh.webp")).into(utkarsh_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "moulisha.webp")).into(moulisha_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "ripu.webp")).into(ripu_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "shashank.webp")).into(shashank_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "shikhar.webp")).into(shikhar_view)
        Glide.with(this).load(Uri.parse(BASE_URL + "deepak.webp")).into(deepak_view)

    }
}