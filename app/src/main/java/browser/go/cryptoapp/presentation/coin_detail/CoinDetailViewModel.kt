package browser.go.cryptoapp.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import browser.go.cryptoapp.common.Constants
import browser.go.cryptoapp.common.Resource
import browser.go.cryptoapp.domain.model.CoinDetail
import browser.go.cryptoapp.domain.use_case.get_coin.GetCoinUseCase
import browser.go.cryptoapp.presentation.dataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CoinDetailViewModel(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(dataState<CoinDetail>())
    val state: State<dataState<CoinDetail>> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = dataState<CoinDetail>(data = result.data)
                }
                is Resource.Error -> {
                    _state.value = dataState<CoinDetail>(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = dataState<CoinDetail>(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}