package com.serbyn.currency_calculator.data.remote

import com.squareup.moshi.Json

data class CurrencyResponse(
    @Json(name = "enname")
    val textName: String,
    @Json(name = "cc")
    val code: String,
    @Json(name = "exchangedate")
    val date: String,
    @Json(name = "rate")
    val rate: Float
)