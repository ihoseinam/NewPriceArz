package com.example.pricearzapplication.Time

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TimeClient {
    private const val Buse_URL ="https://tools.daneshjooyar.com/api/v1/"
    val aoi : TimeInterFace by lazy {
        Retrofit.Builder()
            .baseUrl(Buse_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TimeInterFace::class.java)
    }
}