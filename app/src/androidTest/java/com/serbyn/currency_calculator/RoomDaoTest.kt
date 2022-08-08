package com.serbyn.currency_calculator

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serbyn.currency_calculator.data.local.AppDatabase
import com.serbyn.currency_calculator.data.local.ConvertHistoryDao
import com.serbyn.currency_calculator.data.local.entity.ConvertHistory
import com.serbyn.currency_calculator.data.local.entity.Currency
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var historyDao: ConvertHistoryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        historyDao = db.convertHistoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun writeHistoryAndReadInList() = runTest {
        val history = ConvertHistory(
            1,
            Currency(
                "Name", "UAH", "20.20.2022", 1.0f
            ),
            Currency(
                "Name", "UAH", "20.20.2022", 1.0f
            ),
            fromAmount = 10f,
            toAmount = 100f,
            date = 1010010101
        )
        historyDao.insert(history)
        val items = historyDao.getLastConvertHistory().first()
        assertThat(items[0], equalTo(history))
    }
}