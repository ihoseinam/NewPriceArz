package com.example.pricearzapplication.DataMode.send

data class SendModel(
    val message: String,
    val ok: Boolean,
    val report: List<Report>,
    val success: Int
)