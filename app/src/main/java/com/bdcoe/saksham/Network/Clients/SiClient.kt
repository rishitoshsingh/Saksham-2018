package com.bdcoe.saksham.Network.Clients

import com.bdcoe.saksham.Network.Model
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by rishi on 2/9/18.
 */
interface SiClient {
    @POST("/api/StudentRegister")
    @Headers("Content-Type: application/json")
    fun registerUser(@Body studentRegister: RequestBody): Call<String>
}