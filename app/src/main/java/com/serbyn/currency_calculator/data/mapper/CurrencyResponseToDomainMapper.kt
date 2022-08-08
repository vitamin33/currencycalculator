package com.serbyn.currency_calculator.data.mapper

import com.serbyn.currency_calculator.Mapper
import com.serbyn.currency_calculator.data.remote.CurrencyResponse
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import javax.inject.Inject

open class CurrencyResponseToDomainMapper @Inject constructor() : Mapper<CurrencyResponse, CurrencyItem> {
    override fun invoke(responseItem: CurrencyResponse): CurrencyItem {
        return CurrencyItem(
            responseItem.textName,
            responseItem.code,
            responseItem.date,
            responseItem.rate
        )
    }
}