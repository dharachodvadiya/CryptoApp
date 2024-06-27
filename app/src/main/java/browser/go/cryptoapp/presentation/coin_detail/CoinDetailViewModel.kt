package browser.go.cryptoapp.presentation.coin_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import browser.go.cryptoapp.common.Constants
import browser.go.cryptoapp.common.Resource
import browser.go.cryptoapp.di.AppModule
import browser.go.cryptoapp.domain.model.CoinDetail
import browser.go.cryptoapp.domain.use_case.get_coin.GetCoinUseCase
import browser.go.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import browser.go.cryptoapp.presentation.coin_list.CoinListViewModel
import browser.go.cryptoapp.presentation.dataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(dataState<CoinDetail>())
    val state: State<dataState<CoinDetail>> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
        //getCoin("1")
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repo = AppModule.provideCoinRepository(AppModule.providePaprikaApi())
                val coinUseCase = GetCoinUseCase(repository = repo)
                val savedStateHandle = SavedStateHandle();
                CoinDetailViewModel(getCoinUseCase = coinUseCase, savedStateHandle = savedStateHandle)
            }
        }
    }
}