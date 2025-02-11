package com.bdcoe.saksham.Network.Clients

import com.bdcoe.saksham.POJOs.Medals.MedalsResult
import com.bdcoe.saksham.POJOs.News.NewsResult
import com.bdcoe.saksham.POJOs.Poll.PollResult
import com.bdcoe.saksham.POJOs.RegisterResult
import com.bdcoe.saksham.POJOs.Schedule.ScheduleResult
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
    fun postPolls(@Field("dataflow") dataflow: String,
                  @Field("id") id: String): Call<PollResult>

    @POST("getpoll.php")
    @FormUrlEncoded
    fun getPolls(@Field("id") id: String): Call<PollResult>

    @POST("schedule.php")
    @FormUrlEncoded
    fun getSchedule(@Field("dataflow") dataflow: String,
                    @Field("cat") sport: String): Call<ScheduleResult>

    @POST("register.php")
    @FormUrlEncoded
    fun postRegister(@Field("dataflow") dataflow: String,
                     @Field("name") name: String,
                     @Field("student_no") student_no: String,
                     @Field("male") male: String,
                     @Field("number") number: String,
                     @Field("branch") branch: String,
                     @Field("year") year: String,
                     @Field("hostler") hostler: String,
                     @Field("interest") interest: String): Call<RegisterResult>

}