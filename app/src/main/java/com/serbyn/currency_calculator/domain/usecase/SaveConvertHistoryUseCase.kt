package com.serbyn.currency_calculator.domain.usecase

import android.util.Log
import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import java.lang.Exception
import javax.inject.Inject

open class SaveConvertHistoryUseCase @Inject constructor(
    private val currenciesRepository: CurrencyRepository,
    private val getCurrentDateUseCase: GetCurrentDateLongUseCase
) {
    suspend operator fun invoke(
        fromCurrency: CurrencyItem,
        toCurrency: CurrencyItem,
        fromAmount: String,
        toAmount: String
    ) {
        try {
            val item = ConvertHistoryItem(
                fromCurrency,
                toCurrency,
                fromAmount.toFloat(),
                toAmount.toFloat(),
                getCurrentDateUseCase()
            )
            currenciesRepository.insertConvertHistoryItem(item)
        } catch (e: Exception) {
            Log.e(
                "SaveConvertHistoryUseCase",
                "Error while saving history to database: from - $fromCurrency, to - $toCurrency"
            )
        }
    }
}