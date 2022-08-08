package com.serbyn.currency_calculator.domain.dispatchers;

import kotlinx.coroutines.CoroutineDispatcher;

interface CoroutinesDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}