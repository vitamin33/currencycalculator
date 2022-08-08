package com.serbyn.currency_calculator.data.local.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "history")
data class ConvertHistory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(name = "from_currency") val fromCurrency: Currency,
    @NonNull @ColumnInfo(name = "to_currency") val toCurrency: Currency,
    @NonNull @ColumnInfo(name = "from_amount") val fromAmount: Float,
    @NonNull @ColumnInfo(name = "to_amount") val toAmount: Float,
    @NonNull @ColumnInfo(name = "date") val date: Long
)