package com.example.pricearzapplication.send

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object SendMessage {
    private const val Buse_URl="https://notificator.ir/api/v1/"
    val api:SendInterFace by lazy {
        Retrofit.Builder()
            .baseUrl(Buse_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SendInterFace::class.java)
    }
}