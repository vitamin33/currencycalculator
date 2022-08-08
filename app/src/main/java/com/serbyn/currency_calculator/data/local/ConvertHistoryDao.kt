package com.serbyn.currency_calculator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serbyn.currency_calculator.data.local.entity.ConvertHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ConvertHistoryDao {

    //we need to select only last 10 conversion history
    @Query("SELECT * FROM history ORDER BY date DESC LIMIT 10")
    fun getLastConvertHistory(): Flow<List<ConvertHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: ConvertHistory): Long
}