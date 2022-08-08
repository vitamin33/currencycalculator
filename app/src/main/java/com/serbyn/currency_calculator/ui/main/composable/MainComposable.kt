package com.serbyn.currency_calculator.ui.main.composable

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serbyn.currency_calculator.ui.main.MainViewModel
import com.serbyn.currency_calculator.ui.main.MainContract
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel,
    state: MainContract.State
) {
    BottomSheetCurrencyPicker(viewModel, state) {
        MainContent(navController, viewModel, state)
    }
}

@Composable
fun MainContent(
    navController: NavController,
    viewModel: MainViewModel,
    state: MainContract.State
) {
    BoxWithConstraints(Modifier.height(320.dp)) {
        DatePickerButton(viewModel, state)
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(240.dp)
                .padding(horizontal = 12.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 7.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(10.dp)
            ) {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrencyChooserFields(viewModel, state.firstCurrency, state.enteredValue) {
                        viewModel.sendEvent(MainContract.Event.ToggleCurrencyPicker(true))
                    }
                    CurrencyChooserFields(
                        viewModel,
                        state.secondCurrency,
                        state.resultValue,
                        true
                    ) {
                        viewModel.sendEvent(MainContract.Event.ToggleCurrencyPicker(false))
                    }
                }
            }
            ConvertButton(
                Modifier.align(Alignment.BottomCenter),
                viewModel,
                state.enteredValue
            )
        }
    }
    HistoryWrapperContent(navController, state)
}

@Composable
fun DatePickerButton(viewModel: MainViewModel, state: MainContract.State) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
    ) {
        val context = LocalContext.current
        Button(
            onClick = { showDatePickerDialog(context, viewModel) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 20.dp)
        ) {
            Text(text = state.pickedDate)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

@Composable
fun HistoryWrapperContent(navController: NavController, state: MainContract.State) {

    if (state.error != null) {
        ErrorScreen(error = state.error)
    } else if (state.isLoading) {
        LoadingScreen()
    } else if (state.historyList.isNotEmpty()) {
        HistoryContent(navController, state)
    } else {
        EmptyScreen()
    }
}

@Composable
fun ConvertButton(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    enteredCurrencyValue: String
) {
    Button(
        modifier = modifier,
        onClick = {
            viewModel.sendEvent(
                MainContract.Event.ConvertEnteredValue(enteredCurrencyValue)
            )
        }) {
        Text(text = "Convert")
    }
}

@Composable
fun HistoryContent(navController: NavController, state: MainContract.State) {
    HistoryList(history = state.historyList)
}


@Composable
fun LoadingScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Oops, $error!")
    }
}

@Composable
fun EmptyScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "No history found!")
    }
}

fun showDatePickerDialog(context: Context, viewModel: MainViewModel) {
    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = mCalendar.get(Calendar.YEAR)
    val month = mCalendar.get(Calendar.MONTH)
    val day = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
            viewModel.sendEvent(MainContract.Event.ChooseDate(mYear, mMonth, mDay))
        }, year, month, day
    )
    mDatePickerDialog.show()
}