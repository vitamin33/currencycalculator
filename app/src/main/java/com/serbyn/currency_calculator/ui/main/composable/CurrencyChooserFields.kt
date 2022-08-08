package com.serbyn.currency_calculator.ui.main.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.serbyn.currency_calculator.ui.main.MainContract
import com.serbyn.currency_calculator.ui.main.MainViewModel
import com.serbyn.currency_calculator.ui.main.entity.Currency

@Composable
fun CurrencyChooserFields(
    viewModel: MainViewModel,
    currency: Currency,
    enteredValue: String,
    isReadOnly: Boolean = false,
    onPickCurrencyClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row {
        Box(modifier = Modifier
            .width(180.dp)
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                focusManager.clearFocus()
                onPickCurrencyClick()
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currency.textName,
                    modifier = Modifier
                        .width(118.dp),
                    textAlign = TextAlign.Center,
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.width(30.dp),
                )
            }
        }
        TextField(
            modifier = Modifier.padding(16.dp).focusRequester(focusRequester),
            value = enteredValue,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { viewModel.sendEvent(MainContract.Event.EnterInitValue(it)) },
            readOnly = isReadOnly
        )
    }
}