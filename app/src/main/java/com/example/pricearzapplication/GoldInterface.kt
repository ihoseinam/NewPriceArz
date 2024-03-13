package com.example.pricearzapplication

import com.example.pricearzapplication.data.MainModel
import retrofit2.Response
import retrofit2.http.GET

interface GoldInterface {

    @GET("currencies")
    suspend fun getAllItem():Response<MainModel>

}