package com.serbyn.currency_calculator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.serbyn.currency_calculator.data.local.entity.ConvertHistory

@Database(
    entities = [ConvertHistory::class], // Tell the database the entries will hold data of this type
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun convertHistoryDao(): ConvertHistoryDao
}