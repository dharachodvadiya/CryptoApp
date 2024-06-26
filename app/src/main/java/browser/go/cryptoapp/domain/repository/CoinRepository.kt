package browser.go.cryptoapp.domain.repository

import browser.go.cryptoapp.data.remote.dto.CoinDetailDto
import browser.go.cryptoapp.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoin() : List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}