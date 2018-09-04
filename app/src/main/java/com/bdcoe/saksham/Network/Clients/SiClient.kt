package com.bdcoe.saksham.Network.Clients

import com.bdcoe.saksham.Network.Model
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by rishi on 2/9/18.
 */
interface SiClient {
    @POST("/api/StudentRegister")
    fun registerUser(@Body studentRegister: Model): Call<String>
}