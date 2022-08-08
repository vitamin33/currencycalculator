package com.serbyn.currency_calculator.domain.usecase

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

open class GetCurrentDateUseCase @Inject constructor() {
    operator fun invoke(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return current.format(formatter)
    }
}