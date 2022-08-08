package com.serbyn.currency_calculator.ui.main.mapper

import android.annotation.SuppressLint
import com.serbyn.currency_calculator.Mapper
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import com.serbyn.currency_calculator.ui.main.entity.ConvertHistory
import com.serbyn.currency_calculator.ui.main.entity.Currency
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

open class ConvertHistoryToPresenterMapper @Inject constructor() :
    Mapper<ConvertHistoryItem, ConvertHistory> {

    override fun invoke(history: ConvertHistoryItem): ConvertHistory {

        return ConvertHistory(
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
            date = formatDate(history.date)
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }
}