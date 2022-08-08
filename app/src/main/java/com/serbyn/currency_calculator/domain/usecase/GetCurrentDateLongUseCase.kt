package com.serbyn.currency_calculator.domain.usecase

import javax.inject.Inject

open class GetCurrentDateLongUseCase @Inject constructor() {
    operator fun invoke(): Long {
        return System.currentTimeMillis()
    }
}