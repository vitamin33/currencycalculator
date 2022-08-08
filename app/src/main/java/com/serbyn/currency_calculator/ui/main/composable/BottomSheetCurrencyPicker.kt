@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.serbyn.currency_calculator.ui.main.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serbyn.currency_calculator.ui.base.SIDE_EFFECTS_KEY
import com.serbyn.currency_calculator.ui.main.MainContract
import com.serbyn.currency_calculator.ui.main.MainViewModel
import com.serbyn.currency_calculator.ui.theme.DarkBlue

@ExperimentalMaterialApi
@Composable
fun BottomSheetCurrencyPicker(
    viewModel: MainViewModel,
    state: MainContract.State,
    content: (@Composable () -> Unit)
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val isInitCurrency = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is MainContract.Effect.ToggleCurrencyPicker -> {
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        isInitCurrency.value = effect.isInitCurrency
                        scaffoldState.bottomSheetState.expand()
                    } else {
                        scaffoldState.bottomSheetState.collapse()
                    }
                }
                MainContract.Effect.CloseCurrencyPicker -> {
                    scaffoldState.bottomSheetState.collapse()
                }
                else -> {}
            }
        }
    }

    // Creating a Bottom Sheet
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp),
                            text = "Choose currency:",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Button(
                            modifier = Modifier
                                .background(color = Color.Transparent)
                                .padding(8.dp),
                            onClick = {
                                viewModel.sendEvent(MainContract.Event.CloseCurrencyPicker)
                            }) {
                            Text(text = "Close", fontSize = 18.sp, color = Color.White)
                        }
                    }

                    CurrenciesGrid(viewModel, state, isInitCurrency)
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrenciesGrid(
    viewModel: MainViewModel,
    state: MainContract.State,
    isInitCurrency: MutableState<Boolean>
) {
    val list = state.currencies
    LazyVerticalGrid(
        cells = GridCells.Adaptive(128.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(list.size) { index ->
                Card(
                    backgroundColor = DarkBlue,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.sendEvent(
                                MainContract.Event.PickCurrency(list[index], isInitCurrency.value)
                            )
                        },
                    elevation = 8.dp,
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = list[index].textName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = list[index].code,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    )
}