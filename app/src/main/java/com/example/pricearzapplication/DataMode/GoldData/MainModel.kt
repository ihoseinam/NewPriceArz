package com.example.pricearzapplication.DataMode.GoldData

data class MainModel(
    val `data`: Data,
    val last_update: String,
    val message: String,
    val source: String,
    val success: Int
)