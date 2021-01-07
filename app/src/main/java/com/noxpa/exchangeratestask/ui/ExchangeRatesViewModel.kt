package com.noxpa.exchangeratestask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noxpa.exchangeratestask.model.NbuExchangeRate
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRate
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRatesResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class ExchangeRatesViewModel : ViewModel() {

    private val disposable = CompositeDisposable()
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
        disposable.add(
            repository.getPrivatbankExchangeRates(date)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val list = mutableListOf<PrivatbankExchangeRate>()
                    response.privatbankExchangeRates.forEach {
                        if (isGeneralCurrency(it.currency)) list.add(it)
                    }
                    privatbankExchangeRates.value = list
                }, {})
        )
    }

    private fun getNbuExchangeRates(date: String) {
        disposable.add(
            repository.getNbuExchangeRates(date)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ nbuExchangeRates.value = it }, {})
        )
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

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}