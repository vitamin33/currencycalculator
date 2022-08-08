package com.serbyn.currency_calculator.domain.entity

data class ConvertHistoryItem(
    val fromCurrency: CurrencyItem,
    val toCurrency: CurrencyItem,
    val fromAmount: Float,
    val toAmount: Float,
    val date: Long
)