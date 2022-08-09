package com.serbyn.currency_calculator.ui.main

import androidx.lifecycle.viewModelScope
import com.serbyn.currency_calculator.domain.usecase.*
import com.serbyn.currency_calculator.ui.base.BaseViewModel
import com.serbyn.currency_calculator.ui.main.MainContract.*
import com.serbyn.currency_calculator.ui.main.entity.Currency
import com.serbyn.currency_calculator.ui.main.mapper.ConvertHistoryToPresenterMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadInitialCurrenciesUseCase: LoadInitialCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val saveConvertHistoryUseCase: SaveConvertHistoryUseCase,
    private val loadConvertHistoryUseCase: LoadConvertHistoryUseCase,
    private val formatDateFromPickerUseCase: FormatDateFromPickerUseCase,
    private val formatEnteredInitValueUseCase: FormatEnteredInitValueUseCase,
    private val convertHistoryMapper: ConvertHistoryToPresenterMapper
) : BaseViewModel<Event, State, Effect>() {

    init {
        sendEvent(Event.LoadInitialCurrencies())
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.LoadInitialCurrencies -> {
                launchCurrenciesLoading(event.date)
            }
            is Event.ConvertEnteredValue -> {
                launchCurrencyConvert(event)
            }
            is Event.ChooseDate -> {
                val pickedDate = formatDateFromPickerUseCase(event.year, event.month, event.day)
                launchCurrenciesLoading(pickedDate) {
                    setState { copy(pickedDate = pickedDate) }
                }
            }
            is Event.ToggleCurrencyPicker -> sendEffect(Effect.ToggleCurrencyPicker(event.isFirstCurrency))
            Event.CloseCurrencyPicker -> sendEffect(Effect.CloseCurrencyPicker)
            is Event.EnterInitValue -> setState {
                copy(enteredValue = event.value)
            }
            is Event.PickCurrency -> {
                if (event.isInitCurrency) {
                    setState { copy(firstCurrency = event.currency) }
                } else {
                    setState { copy(secondCurrency = event.currency) }
                }
                sendEffect(Effect.CloseCurrencyPicker)
            }
        }
    }

    private fun launchCurrencyConvert(event: Event.ConvertEnteredValue) {
        viewModelScope.launch {
            val resultValue = convertCurrencyUseCase(
                currentState.firstCurrency.toDomain(),
                currentState.secondCurrency.toDomain(),
                event.enteredValue
            )
            setState { copy(resultValue = resultValue) }

            saveConvertHistoryUseCase(
                currentState.firstCurrency.toDomain(),
                currentState.secondCurrency.toDomain(),
                event.enteredValue,
                resultValue
            )
        }
    }

    private fun launchCurrenciesLoading(date: String?, onFinished: () -> Unit = {}) {
        viewModelScope.launch {
            loadInitialCurrenciesUseCase(date)
                .onStart { setState { copy(isLoading = true) } }
                .map { it.map(::Currency) }
                .catch {
                    setState { copy(error = "Error while loading currencies from API.") }
                }
                .collect {
                    setState {
                        copy(
                            firstCurrency = it[0],
                            secondCurrency = it[1],
                            currencies = it,
                            isLoading = false
                        )
                    }
                    onFinished()
                }
            loadConvertHistoryUseCase().map {
                it.map(convertHistoryMapper)
            }
                .catch {
                    setState { copy(error = "Error while loading currencies from database.") }
                }
                .collect {
                    setState { copy(historyList = it) }
                }
        }
    }

    override fun initState() = State(
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
}