package com.noxpa.exchangeratestask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noxpa.exchangeratestask.model.NbuExchangeRate
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRate
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRatesResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class ExchangeRatesViewModel : ViewModel() {

    private val repository = ExchangeRatesRepositoryImpl()

    val selectedDate = MutableLiveData<String>()

    val privatbankExchangeRates = MutableLiveData<List<PrivatbankExchangeRate>>()
    val privatbankSelectedCurrency = MutableLiveData<String>()

    val nbuExchangeRates = MutableLiveData<List<NbuExchangeRate>>()
    val nbuSelectedCurrency = MutableLiveData<Int>()

    init { onDateSet(Calendar.getInstance().time) }

    fun onDateSet(date: Date) {
        selectedDate.value = getFormattedDate("dd.MM.yyyy", date)

        getPrivatbankExchangeRates(getFormattedDate("dd.MM.yyyy", date))
        getNbuExchangeRates(getFormattedDate("yyyyMMdd", date))
    }

    fun onPrivatbankCurrencySelect(currency: String) {
        setPrivatbankSelectedCurrency(currency)
        setNbuSelectedCurrency(currency)
    }

    private fun getPrivatbankExchangeRates(date: String) {
        repository.getPrivatbankExchangeRates(date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<PrivatbankExchangeRatesResponse> {
                override fun onSuccess(t: PrivatbankExchangeRatesResponse) {
                    val list = mutableListOf<PrivatbankExchangeRate>()
                    t.privatbankExchangeRates.forEach {
                        if (isGeneralCurrency(it.currency)) list.add(it)
                    }
                    privatbankExchangeRates.value = list
                }

                override fun onError(e: Throwable) {}
                override fun onSubscribe(d: Disposable) {}
            })
    }

    private fun getNbuExchangeRates(date: String) {
        repository.getNbuExchangeRates(date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<List<NbuExchangeRate>> {
                override fun onSuccess(t: List<NbuExchangeRate>) {
                    nbuExchangeRates.value = t
                }

                override fun onError(e: Throwable) {}
                override fun onSubscribe(d: Disposable) {}
            })
    }

    private fun setPrivatbankSelectedCurrency(currency: String) {
        privatbankSelectedCurrency.value = currency
    }

    private fun setNbuSelectedCurrency(currency: String) {
        nbuExchangeRates.value?.forEachIndexed { index, nbuExchangeRate ->
            if (nbuExchangeRate.currencyTextCode == currency) {
                nbuSelectedCurrency.value = index
                return
            }
        }
    }

    private fun getFormattedDate(pattern: String, date: Date): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }

    private fun isGeneralCurrency(currency: String): Boolean {
        return currency == "USD" || currency == "EUR" || currency == "RUB"
    }
}