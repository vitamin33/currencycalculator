package com.serbyn.currency_calculator.data.mapper

import com.serbyn.currency_calculator.Mapper
import com.serbyn.currency_calculator.data.local.entity.ConvertHistory
import com.serbyn.currency_calculator.data.local.entity.Currency
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import javax.inject.Inject

open class ConvertHistoryToDataMapper @Inject constructor() :
    Mapper<ConvertHistoryItem, ConvertHistory> {

    override fun invoke(history: ConvertHistoryItem): ConvertHistory {

        return ConvertHistory(
            id = 0,
            fromCurrency = Currency(
                history.fromCurrency.textName,
                history.fromCurrency.code,
                history.fromCurrency.date,
                history.fromCurrency.rate
            ),
            toCurrency = Currency(
                history.toCurrency.textName,
                history.toCurrency.code,
                history.toCurrency.date,
                history.toCurrency.rate
            ),
            fromAmount = history.fromAmount,
            toAmount = history.toAmount,
            date = history.date
        )
    }
}