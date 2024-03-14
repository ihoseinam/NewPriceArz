package com.example.pricearzapplication.Gold

import com.example.pricearzapplication.DataMode.GoldData.MainModel
import retrofit2.Response
import retrofit2.http.GET

interface GoldInterface {

    @GET("currencies")
    suspend fun getAllItem():Response<MainModel>

}