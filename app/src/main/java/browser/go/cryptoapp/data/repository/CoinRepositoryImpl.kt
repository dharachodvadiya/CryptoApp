package browser.go.cryptoapp.data.repository

import browser.go.cryptoapp.data.remote.CoinPaprikaApi
import browser.go.cryptoapp.data.remote.dto.CoinDetailDto
import browser.go.cryptoapp.data.remote.dto.CoinDto
import browser.go.cryptoapp.domain.repository.CoinRepository

class CoinRepositoryImpl(
    private val api : CoinPaprikaApi
    ) : CoinRepository {

    override suspend fun getCoin(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}