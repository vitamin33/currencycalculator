package com.serbyn.currency_calculator.domain.usecase

import android.util.Log
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

open class ConvertCurrencyUseCase @Inject constructor() {
    operator fun invoke(
        initCurrency: CurrencyItem?,
        resultCurrency: CurrencyItem?,
        firstCurrencyValue: String
    ): String {
        if (initCurrency == null || resultCurrency == null) {
            return "0"
        }
        return try {
            val firstValueFloat = firstCurrencyValue.toFloat()
            val firstCurrencyValueInUah = initCurrency.rate * firstValueFloat
            val result = firstCurrencyValueInUah / resultCurrency.rate
            return result.toString()
        } catch (e: Exception) {
            Log.e(
                "FormatEnteredInitValueUseCase",
                "Error while converting: $firstCurrencyValue"
            )
            "0"
        }

    }

}