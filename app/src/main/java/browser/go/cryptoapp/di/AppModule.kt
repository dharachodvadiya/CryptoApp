package browser.go.cryptoapp.di

import browser.go.cryptoapp.common.Constants
import browser.go.cryptoapp.data.remote.CoinPaprikaApi
import browser.go.cryptoapp.data.repository.CoinRepositoryImpl
import browser.go.cryptoapp.domain.repository.CoinRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    fun providePaprikaApi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}