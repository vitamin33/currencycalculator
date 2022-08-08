package com.serbyn.currency_calculator.data.local

import androidx.room.TypeConverter
import com.serbyn.currency_calculator.data.local.entity.Currency
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

class Converters() {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val jsonAdapter: JsonAdapter<Currency> = moshi.adapter(Currency::class.java)

    @TypeConverter
    fun gsonToCurrency(json: String): Currency? {
        return jsonAdapter.fromJson(json)
    }

    @TypeConverter
    fun currencyToGson(currency: Currency): String? {
        return jsonAdapter.toJson(currency)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}