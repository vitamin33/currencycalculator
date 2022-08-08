package com.serbyn.currency_calculator.data.remote

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("NBU_Exchange/exchange_site?sort=exchangedate&order=desc&json")
    suspend fun getCurrencyList(
        @Query("start") startDate: String,
        @Query("end") endDate: String
    ): List<CurrencyResponse>

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create(CurrencyApiService::class.java)
    }
}