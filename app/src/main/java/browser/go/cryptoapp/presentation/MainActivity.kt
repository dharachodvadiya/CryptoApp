package browser.go.cryptoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import browser.go.cryptoapp.presentation.coin_detail.CoinDetailScreen
import browser.go.cryptoapp.presentation.coin_detail.CoinDetailViewModel
import browser.go.cryptoapp.presentation.coin_list.CoinListScreen
import browser.go.cryptoapp.presentation.coin_list.CoinListViewModel
import browser.go.cryptoapp.presentation.ui.theme.CryptoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CoinListScreen.route
                    ) {
                        composable(
                            route = Screen.CoinListScreen.route
                        ) {
                            val coinListViewModel : CoinListViewModel =
                                viewModel(factory = CoinListViewModel.Factory)
                            CoinListScreen(navController,coinListViewModel)
                        }
                        composable(
                            route = Screen.CoinDetailScreen.route + "/{coinId}"
                        ) {
                            val coinDetailViewModel : CoinDetailViewModel =
                                viewModel(factory = CoinDetailViewModel.Factory)
                            CoinDetailScreen(coinDetailViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoAppTheme {
        Greeting("Android")
    }
}