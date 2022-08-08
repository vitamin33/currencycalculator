package com.serbyn.currency_calculator.domain

import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun getCurrencyList(date: String) : Flow<List<CurrencyItem>>

    suspend fun getConvertHistoryList() : Flow<List<ConvertHistoryItem>>

    suspend fun insertConvertHistoryItem(item: ConvertHistoryItem)
}