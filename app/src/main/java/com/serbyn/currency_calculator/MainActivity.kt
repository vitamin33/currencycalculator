package com.serbyn.currency_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serbyn.currency_calculator.ui.main.MainContract
import com.serbyn.currency_calculator.ui.main.MainViewModel
import com.serbyn.currency_calculator.ui.main.composable.MainScreen
import com.serbyn.currency_calculator.ui.theme.CurrencycalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencycalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val state: MainContract.State by viewModel.uiState.collectAsStateLifecycleAware()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController, viewModel, state) }
        //TODO add more screens in the future
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencycalculatorTheme {
        MyApp()
    }
}