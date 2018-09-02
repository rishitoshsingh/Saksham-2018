package com.bdcoe.saksham.Network.Clients

import com.bdcoe.saksham.POJOs.Medals.MedalsResult
import com.bdcoe.saksham.POJOs.News.NewsResult
import com.bdcoe.saksham.POJOs.Poll.PollResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by rishi on 2/9/18.
 */
interface BdcoeClient {

    @POST("medals.php")
    @FormUrlEncoded
    fun getMedalas(@Field("dataflow") datadlow:String ): Call<MedalsResult>

    @POST("news.php")
    @FormUrlEncoded
    fun getNews(@Field("dataflow") dataflow: String): Call<NewsResult>

    @POST("poll.php")
    @FormUrlEncoded
    fun getPolls(@Field("dataflow") dataflow: String): Call<PollResult>

}