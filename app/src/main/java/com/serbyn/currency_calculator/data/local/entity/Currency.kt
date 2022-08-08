package com.serbyn.currency_calculator.data.local.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currency(
    @Json(name = "text_name") val textName: String,
    @Json(name = "code") val code: String,
    @Json(name = "date") val date: String,
    @Json(name = "rate") val rate: Float
)