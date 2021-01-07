package com.noxpa.exchangeratestask.model

import com.google.gson.annotations.SerializedName

data class PrivatbankExchangeRatesResponse(

    @SerializedName("date")
    val date : String,

    @SerializedName("bank")
    val bank : String,

    @SerializedName("baseCurrency")
    val baseCurrency : Int,

    @SerializedName("baseCurrencyLit")
    val baseCurrencyLit : String,

    @SerializedName("exchangeRate")
    val privatbankExchangeRates : List<PrivatbankExchangeRate>
)