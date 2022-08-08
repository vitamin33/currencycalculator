package com.serbyn.currency_calculator.ui.main.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serbyn.currency_calculator.ui.main.entity.ConvertHistory

@Composable
fun HistoryList(history: List<ConvertHistory>) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Convert History: ",
            fontSize = 16.sp
        )
        LazyColumn {
            items(history) { item ->
                HistoryRow(item)
            }
        }
    }
}

@Composable
fun HistoryRow(item: ConvertHistory) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().clickable{},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(0.8f),
                text = item.date,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.fromAmount}  ${item.fromCurrency.code}",
                textAlign = TextAlign.Center
            )
            Icon(Icons.Default.ArrowForward, null)
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.toAmount}  ${item.toCurrency.code}",
                textAlign = TextAlign.Center
            )
        }
    }
}