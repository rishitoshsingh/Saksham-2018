package com.bdcoe.saksham.Network.Clients

import com.bdcoe.saksham.POJOs.Medals.MedalsResult
import com.bdcoe.saksham.POJOs.News.NewsResult
import com.bdcoe.saksham.POJOs.Poll.PollResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by rishi on 2/9/18.
 */
interface BdcoeClient {

    @POST("medals.php")
    fun getMedalas(@Body dataflow: Int): Call<MedalsResult>

    @POST("news.php")
    fun getNews(@Body dataflow: Int): Call<NewsResult>

    @POST("poll.php")
    fun getPolls(@Body dataflow: Int): Call<PollResult>

}