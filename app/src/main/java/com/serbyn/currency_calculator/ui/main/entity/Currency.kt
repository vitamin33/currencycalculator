package com.serbyn.currency_calculator.ui.main.entity

import com.serbyn.currency_calculator.domain.entity.CurrencyItem

data class Currency(
    val textName: String,
    val code: String,
    val date: String,
    val rate: Float
) {
    constructor(item: CurrencyItem) : this(
        item.textName,
        item.code,
        item.date,
        item.rate
    )

    fun toDomain() : CurrencyItem {
        return CurrencyItem(textName, code, date, rate)
    }

    companion object {
        fun defaultCurrency() : Currency{
            return Currency("", "---", "", 1f)
        }
    }
}