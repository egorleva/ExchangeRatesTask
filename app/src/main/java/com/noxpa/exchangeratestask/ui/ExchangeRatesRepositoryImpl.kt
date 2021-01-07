package com.noxpa.exchangeratestask.ui

import com.noxpa.exchangeratestask.model.NbuExchangeRate
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRatesResponse
import com.noxpa.exchangeratestask.network.NetworkService
import io.reactivex.Single

class ExchangeRatesRepositoryImpl : ExchangeRatesRepository {

    private val privatbankApi = NetworkService.getExchangeRatesPrivatbankApi()
    private val nbuApi = NetworkService.getExchangeRatesNbuApi()

    override fun getPrivatbankExchangeRates(date: String): Single<PrivatbankExchangeRatesResponse> {
        return privatbankApi.getExchangeRates(date)
    }

    override fun getNbuExchangeRates(date: String): Single<List<NbuExchangeRate>> {
        return nbuApi.getExchangeRates(date)
    }
}