package com.serbyn.currency_calculator.domain.usecase

import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

open class LoadConvertHistoryUseCase @Inject constructor(
    private val currenciesRepository: CurrencyRepository
) {
    suspend operator fun invoke() : Flow<List<ConvertHistoryItem>> {
        return currenciesRepository.getConvertHistoryList()
    }
}