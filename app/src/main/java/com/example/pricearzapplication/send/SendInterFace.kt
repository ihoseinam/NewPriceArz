package com.example.pricearzapplication.send

import com.example.pricearzapplication.DataMode.send.SendModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SendInterFace {
    @FormUrlEncoded
    @POST("send")
    suspend fun sendMessageToTelegram(
        @Field("to") token: String,
        @Field("text") text: String,
    ): Response<SendModel>
}