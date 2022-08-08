package com.serbyn.currency_calculator.data

import com.serbyn.currency_calculator.data.local.ConvertHistoryDao
import com.serbyn.currency_calculator.data.mapper.ConvertHistoryToDataMapper
import com.serbyn.currency_calculator.data.mapper.ConvertHistoryToDomainMapper
import com.serbyn.currency_calculator.data.mapper.CurrencyResponseToDomainMapper
import com.serbyn.currency_calculator.data.remote.CurrencyApiService
import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.currency_calculator.domain.entity.ConvertHistoryItem
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val dispatchers: CoroutinesDispatchers,
    private val remoteCurrencyApi: CurrencyApiService,
    private val convertHistoryDao: ConvertHistoryDao,
    private val responseToDomainMapper: CurrencyResponseToDomainMapper,
    private val historyToDomainMapper: ConvertHistoryToDomainMapper,
    private val historyToDataMapper: ConvertHistoryToDataMapper
) : CurrencyRepository {
    override suspend fun getCurrencyList(date: String): Flow<List<CurrencyItem>> {
        return flow {
            emit(getRemoteCurrencyList(date))
        }
    }

    override suspend fun getConvertHistoryList(): Flow<List<ConvertHistoryItem>> {
        return withContext(dispatchers.io) {
            return@withContext convertHistoryDao.getLastConvertHistory().map {
                it.map(historyToDomainMapper)
            }
        }
    }

    override suspend fun insertConvertHistoryItem(item: ConvertHistoryItem) {
        return withContext(dispatchers.io) {
            convertHistoryDao.insert(historyToDataMapper(item))
        }
    }

    private suspend fun getRemoteCurrencyList(date: String) : List<CurrencyItem> {
        return withContext(dispatchers.io) {

            val list = mutableListOf<CurrencyItem>()

            //add UAH because REST APi doesn't return it
            list.add(CurrencyItem("Ukr. Hrivna", "UAH", date, 1f))

            //we need only one day, so "start" equals "end" date
            list.addAll(remoteCurrencyApi.getCurrencyList(date, date).map(responseToDomainMapper))

            return@withContext list
        }
    }
}