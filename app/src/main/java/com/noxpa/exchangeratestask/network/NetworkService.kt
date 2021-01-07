package com.noxpa.exchangeratestask.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val BASE_URL_PRIVATBANK = "https://api.privatbank.ua/p24api/"
    private const val BASE_URL_NBU = "https://bank.gov.ua/NBUStatService/v1/statdirectory/"

    private val retrofitClientPrivatbank = Retrofit.Builder()
        .baseUrl(BASE_URL_PRIVATBANK)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val retrofitClientNbu = Retrofit.Builder()
        .baseUrl(BASE_URL_NBU)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getExchangeRatesPrivatbankApi(): ExchangeRatesPrivatbankApi {
        return retrofitClientPrivatbank.create(ExchangeRatesPrivatbankApi::class.java)
    }

    fun getExchangeRatesNbuApi(): ExchangeRatesNbuApi {
        return retrofitClientNbu.create(ExchangeRatesNbuApi::class.java)
    }
}