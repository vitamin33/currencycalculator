package com.serbyn.currency_calculator.domain.usecase

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

open class FormatDateFromPickerUseCase @Inject constructor() {
    @SuppressLint("SimpleDateFormat")
    operator fun invoke(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(calendar.time)
    }
}