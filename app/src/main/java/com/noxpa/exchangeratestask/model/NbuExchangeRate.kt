package com.noxpa.exchangeratestask.model

import com.google.gson.annotations.SerializedName

data class NbuExchangeRate(

    @SerializedName("r030")
    val currencyNumberCode : Int,

    @SerializedName("txt")
    val currencyDescription : String,

    @SerializedName("rate")
    val exchangeRate : Double,

    @SerializedName("cc")
    val currencyTextCode : String,

    @SerializedName("exchangedate")
    val exchangeDate : String
)