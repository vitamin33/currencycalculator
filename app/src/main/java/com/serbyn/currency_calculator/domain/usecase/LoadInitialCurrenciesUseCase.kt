package com.serbyn.currency_calculator.domain.usecase

import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

open class LoadInitialCurrenciesUseCase @Inject constructor(
    private val currenciesRepository: CurrencyRepository,
    private val getCurrentDateUseCase: GetCurrentDateUseCase
    ) {
    suspend operator fun invoke(date: String?): Flow<List<CurrencyItem>> {
        val resultDate = date ?: getCurrentDateUseCase()
        val finalDate = resultDate.replace(".", "")
        return currenciesRepository.getCurrencyList(finalDate)
    }
}