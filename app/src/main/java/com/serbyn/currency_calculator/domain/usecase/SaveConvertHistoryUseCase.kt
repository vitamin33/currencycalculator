package com.serbyn.currency_calculator.domain.usecase

import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import javax.inject.Inject

open class SaveConvertHistoryUseCase @Inject constructor(
    private val currenciesRepository: CurrencyRepository,
    private val getCurrentDateUseCase: GetCurrentDateLongUseCase
) {
    suspend operator fun invoke(
        fromCurrency: CurrencyItem,
        toCurrency: CurrencyItem,
        fromAmount: Float,
        toAmount: Float
    ) {

        val item = ConvertHistoryItem(
            fromCurrency,
            toCurrency,
            fromAmount,
            toAmount,
            getCurrentDateUseCase()
        )
        currenciesRepository.insertConvertHistoryItem(item)
    }
}