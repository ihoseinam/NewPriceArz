package com.example.pricearzapplication.DataMode.GoldData

data class Data(
    val cryptocurrencies: List<Cryptocurrency>,
    val currencies: List<Currency>,
    val golds: List<Gold>
)