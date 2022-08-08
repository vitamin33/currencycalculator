package com.serbyn.currency_calculator.domain.entity

data class CurrencyItem(
    val textName: String,
    val code: String,
    val date: String,
    val rate: Float
)