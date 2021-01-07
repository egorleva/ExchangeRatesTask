package com.noxpa.exchangeratestask.network

import com.noxpa.exchangeratestask.model.NbuExchangeRate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesNbuApi {

    @GET("exchangenew?json")
    fun getExchangeRates(@Query("date") date: String): Single<List<NbuExchangeRate>>
}