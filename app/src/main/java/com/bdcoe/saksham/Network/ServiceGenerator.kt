package com.bdcoe.saksham.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rishi on 2/9/18.
 */
class ServiceGenerator {

        companion object {

            private val bdcoeBuilder: Retrofit.Builder = Retrofit.Builder()
                    .baseUrl("http://saksham2015.pe.hu/")
                    .addConverterFactory(GsonConverterFactory.create())
            private val retrofit:Retrofit = bdcoeBuilder.build()

            private val siBuilder:Retrofit.Builder = Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/youtube/v3/")
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