package com.serbyn.currency_calculator.domain.usecase

import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import javax.inject.Inject

open class ConvertCurrencyUseCase @Inject constructor() {
    operator fun invoke(
        initCurrency: CurrencyItem?,
        resultCurrency: CurrencyItem?,
        firstCurrencyValue: Float
    ): String {
        if (initCurrency == null || resultCurrency == null) {
            return ""
        }
        val firstCurrencyValueInUah = initCurrency.rate * firstCurrencyValue
        val result = firstCurrencyValueInUah / resultCurrency.rate
        return result.toString()
    }

}