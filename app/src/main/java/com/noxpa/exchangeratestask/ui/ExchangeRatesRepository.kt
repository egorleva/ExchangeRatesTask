package com.noxpa.exchangeratestask.ui

import com.noxpa.exchangeratestask.model.NbuExchangeRate
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRatesResponse
import io.reactivex.Single

interface ExchangeRatesRepository {

    fun getPrivatbankExchangeRates(date: String): Single<PrivatbankExchangeRatesResponse>
    fun getNbuExchangeRates(date: String): Single<List<NbuExchangeRate>>
}