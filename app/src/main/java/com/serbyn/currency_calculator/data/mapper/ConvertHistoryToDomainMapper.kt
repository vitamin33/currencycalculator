package com.serbyn.currency_calculator.data.mapper

import com.serbyn.currency_calculator.Mapper
import com.serbyn.currency_calculator.data.local.entity.ConvertHistory
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

open class ConvertHistoryToDomainMapper @Inject constructor() :
    Mapper<ConvertHistory, ConvertHistoryItem> {

    override fun invoke(history: ConvertHistory): ConvertHistoryItem {

        return ConvertHistoryItem(
            fromCurrency = CurrencyItem(
                history.fromCurrency.textName,
                history.fromCurrency.code,
                history.fromCurrency.date,
                history.fromCurrency.rate
            ),
            toCurrency = CurrencyItem(
                history.toCurrency.textName,
                history.toCurrency.code,
                history.toCurrency.date,
                history.toCurrency.rate
            ),
            fromAmount = history.fromAmount,
            toAmount = formatToTwoDecimals(history.toAmount),
            date = history.date
        )
    }

    private fun formatToTwoDecimals(amount: Float): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(amount).toFloat()
    }
}