package com.serbyn.currency_calculator.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serbyn.currency_calculator.TestCoroutineRule
import com.serbyn.currency_calculator.domain.CurrencyRepository
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import com.serbyn.currency_calculator.domain.usecase.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class UseCasesTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutinesRule: TestCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var currenciesRepository: CurrencyRepository

    @Mock
    private lateinit var getCurrentDateUseCase: GetCurrentDateUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `When run LoadInitialCurrenciesUseCase with date with points then should return list`() =
        runTest {
            // Given
            val expectedCurrencies = listOf(
                CurrencyItem(
                    "Hrivna",
                    "UAH",
                    "11.11.2011",
                    1.0f
                ),
                CurrencyItem(
                    "Dollar",
                    "USD",
                    "11.11.2011",
                    20.0f
                )
            )

            currenciesRepository = mockk()
            coEvery {
                currenciesRepository.getCurrencyList("20202022")
            } returns flow {
                emit(expectedCurrencies)
            }

            // When
            val loadInitialUseCase = LoadInitialCurrenciesUseCase(
                currenciesRepository,
                getCurrentDateUseCase
            )
            val firstItemInFlow = loadInitialUseCase("20.20.2022").first()
            advanceUntilIdle()

            // Then
            Assert.assertEquals(expectedCurrencies, firstItemInFlow)
        }

    @Test
    fun `When run LoadInitialCurrenciesUseCase without date then should return list`() =
        runTest {
            // Given
            val expectedCurrencies = listOf(
                CurrencyItem(
                    "Hrivna",
                    "UAH",
                    "11.11.2011",
                    1.0f
                ),
                CurrencyItem(
                    "Dollar",
                    "USD",
                    "11.11.2011",
                    20.0f
                )
            )

            currenciesRepository = mockk()
            coEvery {
                currenciesRepository.getCurrencyList("20202022")
            } returns flow {
                emit(expectedCurrencies)
            }
            getCurrentDateUseCase = mockk()
            coEvery {
                getCurrentDateUseCase()
            } returns "20202022"

            // When
            val loadInitialUseCase = LoadInitialCurrenciesUseCase(
                currenciesRepository,
                getCurrentDateUseCase
            )
            val firstItemInFlow = loadInitialUseCase(null).first()
            advanceUntilIdle()

            // Then
            Assert.assertEquals(expectedCurrencies, firstItemInFlow)
        }
}