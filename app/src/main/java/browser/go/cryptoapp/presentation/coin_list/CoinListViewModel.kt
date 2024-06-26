package browser.go.cryptoapp.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import browser.go.cryptoapp.common.Resource
import browser.go.cryptoapp.domain.model.Coin
import browser.go.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import browser.go.cryptoapp.presentation.dataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CoinListViewModel (
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(dataState<List<Coin>>())
    val state: State<dataState<List<Coin>>> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = dataState<List<Coin>>(data = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = dataState<List<Coin>>(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = dataState<List<Coin>>(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}