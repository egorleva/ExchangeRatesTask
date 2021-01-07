package com.noxpa.exchangeratestask.network

import com.noxpa.exchangeratestask.model.PrivatbankExchangeRatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesPrivatbankApi {

    @GET("exchange_rates?json")
    fun getExchangeRates(@Query("date") date: String): Single<PrivatbankExchangeRatesResponse>
}