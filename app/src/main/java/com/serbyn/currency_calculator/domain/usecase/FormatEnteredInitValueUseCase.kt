package com.serbyn.currency_calculator.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

open class FormatEnteredInitValueUseCase @Inject constructor() {
    @SuppressLint("SimpleDateFormat")
    operator fun invoke(amount: String): String {
        return try {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            df.format(amount.toFloat())
        } catch (e: Exception) {
            Log.e("FormatEnteredInitValueUseCase", "Error while formatting: $amount")
            "0"
        }
    }
}