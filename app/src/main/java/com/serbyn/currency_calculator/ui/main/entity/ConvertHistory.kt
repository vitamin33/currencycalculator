package com.serbyn.currency_calculator.ui.main.entity

data class ConvertHistory(
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val fromAmount: Float,
    val toAmount: Float,
    val date: String
)