package com.bdcoe.saksham.Network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rishi on 2/9/18.
 */
class ServiceGenerator {

        companion object {

            private val builder: OkHttpClient.Builder  =  OkHttpClient.Builder()

            private val logging: HttpLoggingInterceptor =  HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)

            val log  = builder.interceptors().add(logging)

            private val client: OkHttpClient = builder.build()

            private val bdcoeBuilder: Retrofit.Builder = Retrofit.Builder()
                    .baseUrl("http://saksham2015.pe.hu/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
            private val retrofit:Retrofit = bdcoeBuilder.build()

            private val siBuilder:Retrofit.Builder = Retrofit.Builder()
                    .baseUrl("http://117.55.241.44:8074/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
            private val siRetrofit:Retrofit = siBuilder.build()


            fun <S> createBdcoeService(serviceClass: Class<S>):S {
                return retrofit.create(serviceClass)
            }

            fun <S> createSiService(serviceClass: Class<S>):S {
                return siRetrofit.create(serviceClass)
            }

        }


}