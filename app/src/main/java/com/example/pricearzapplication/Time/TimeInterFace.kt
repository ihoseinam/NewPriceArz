package com.example.pricearzapplication.Time

import com.example.pricearzapplication.DataMode.TimeData.Time
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeInterFace {

    @GET("date/now")
   suspend fun getTime(
        @Query("short") short: Boolean,
    ): Response<Time>
}