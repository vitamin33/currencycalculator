@file:OptIn(ExperimentalMaterialApi::class)

package com.serbyn.currency_calculator.ui.main

import androidx.compose.material.ExperimentalMaterialApi
import com.serbyn.currency_calculator.ui.base.UiEffect
import com.serbyn.currency_calculator.ui.base.UiEvent
import com.serbyn.currency_calculator.ui.base.UiState
import com.serbyn.currency_calculator.ui.main.entity.ConvertHistory
import com.serbyn.currency_calculator.ui.main.entity.Currency

interface MainContract {

    sealed class Event : UiEvent {
        data class ChooseDate(
            val year: Int, val month: Int, val day: Int
        ) : Event()

        data class LoadInitialCurrencies(val date: String? = null) : Event()
        data class ToggleCurrencyPicker(val isFirstCurrency: Boolean) : Event()
        data class PickCurrency(val currency: Currency, val isInitCurrency: Boolean) : Event()
        data class ConvertEnteredValue(val enteredValue: String) : Event()
        data class EnterInitValue(val value: String) : Event()
        object CloseCurrencyPicker : Event()
    }

    //UI view state
    data class State(
        val firstCurrency: Currency,
        val secondCurrency: Currency,
        val enteredValue: String,
        val resultValue: String,
        val pickedDate: String,
        val historyList: List<ConvertHistory>,
        val currencies: List<Currency>,
        val error: String?,
        val isLoading: Boolean
    ) : UiState

    sealed class Effect : UiEffect {
        object CloseCurrencyPicker : Effect()
        data class ToggleCurrencyPicker(val isInitCurrency: Boolean) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}