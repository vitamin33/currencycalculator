package com.serbyn.currency_calculator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serbyn.currency_calculator.TestCoroutineRule
import com.serbyn.currency_calculator.domain.entity.CurrencyItem
import com.serbyn.currency_calculator.domain.usecase.*
import com.serbyn.currency_calculator.ui.main.MainContract
import com.serbyn.currency_calculator.ui.main.MainViewModel
import com.serbyn.currency_calculator.ui.main.entity.Currency
import com.serbyn.currency_calculator.ui.main.mapper.ConvertHistoryToPresenterMapper
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutinesRule: TestCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var loadInitialCurrenciesUseCase: LoadInitialCurrenciesUseCase

    @Mock
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase

    @Mock
    private lateinit var saveConvertHistoryUseCase: SaveConvertHistoryUseCase

    @Mock
    private lateinit var loadConvertHistoryUseCase: LoadConvertHistoryUseCase

    @Mock
    private lateinit var formatDateFromPickerUseCase: FormatDateFromPickerUseCase

    @Mock
    private lateinit var convertHistoryMapper: ConvertHistoryToPresenterMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private val testDispatcher = StandardTestDispatcher()


    @Test
    fun `When view model initialized then should emit initial view state first`() = runTest {
        // Given
        val expectedInitialViewState = MainContract.State(
            Currency.defaultCurrency(),
            Currency.defaultCurrency(),
            enteredValue = "1",
            resultValue = "",
            pickedDate = "Pick date",
            emptyList(),
            emptyList(),
            error = null,
            isLoading = false
        )

        // When
        val viewModel = MainViewModel(
            loadInitialCurrenciesUseCase,
            convertCurrencyUseCase,
            saveConvertHistoryUseCase,
            loadConvertHistoryUseCase,
            formatDateFromPickerUseCase,
            convertHistoryMapper
        )

        // Then
        assertEquals(expectedInitialViewState, viewModel.uiState.value)
    }

    @Test
    fun `When send LoadInitialCurrencies event then should emit a view state`() = runTest {
        // Given
        val currencies = listOf(
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
        val expectedViewState = MainContract.State(
            Currency(currencies[0]),
            Currency(currencies[1]),
            enteredValue = "1",
            resultValue = "",
            pickedDate = "Pick date",
            currencies = currencies.map(::Currency),
            historyList = emptyList(),
            error = null,
            isLoading = false
        )

        loadInitialCurrenciesUseCase = mockk()
        loadConvertHistoryUseCase = mockk()
        coEvery { loadInitialCurrenciesUseCase.invoke(null) } returns flow { emit(currencies) }
        coEvery { loadConvertHistoryUseCase.invoke() } returns flow { emit(emptyList()) }


        // When
        val viewModel = MainViewModel(
            loadInitialCurrenciesUseCase,
            convertCurrencyUseCase,
            saveConvertHistoryUseCase,
            loadConvertHistoryUseCase,
            formatDateFromPickerUseCase,
            convertHistoryMapper
        )
        viewModel.sendEvent(MainContract.Event.LoadInitialCurrencies(null))
        advanceUntilIdle()

        // Then
        assertEquals(expectedViewState, viewModel.currentState)
    }
}