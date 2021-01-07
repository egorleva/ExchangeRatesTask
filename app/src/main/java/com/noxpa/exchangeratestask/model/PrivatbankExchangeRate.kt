package com.noxpa.exchangeratestask.model

import com.google.gson.annotations.SerializedName

data class PrivatbankExchangeRate(

    @SerializedName("baseCurrency")
    val baseCurrency : String,

    @SerializedName("currency")
    val currency : String,

    @SerializedName("saleRateNB")
    val saleRateNB : Double,

    @SerializedName("purchaseRateNB")
    val purchaseRateNB : Double,

    @SerializedName("saleRate")
    val saleRate : Double,

    @SerializedName("purchaseRate")
    val purchaseRate : Double
)