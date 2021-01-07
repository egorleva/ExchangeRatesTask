package com.noxpa.exchangeratestask.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.noxpa.exchangeratestask.ui.fragments.DatePickerFragment
import com.noxpa.exchangeratestask.R
import com.noxpa.exchangeratestask.ui.adapters.NbuExchangeRatesAdapter
import com.noxpa.exchangeratestask.ui.adapters.PrivatbankExchangeRatesAdapter
import kotlinx.android.synthetic.main.activity_exchange_rates.*
import java.util.*

class ExchangeRatesActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener,
    PrivatbankExchangeRatesAdapter.OnPrivatbankCurrencySelectListener {

    private lateinit var viewModel: ExchangeRatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rates)

        val privatbankExchangeRatesAdapter = PrivatbankExchangeRatesAdapter(this)
        val nbuExchangeRatesAdapter = NbuExchangeRatesAdapter()

        privatbank_exchange_rates_recycler_view.adapter = privatbankExchangeRatesAdapter
        privatbank_exchange_rates_date_text_view.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, null)
        }

        nbu_exchange_rates_recycler_view.adapter = nbuExchangeRatesAdapter
        nbu_exchange_rates_date_text_view.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, null)
        }

        viewModel = ViewModelProvider(this).get(ExchangeRatesViewModel::class.java)

        viewModel.selectedDate.observe(this) {
            privatbank_exchange_rates_date_text_view.text = it
            nbu_exchange_rates_date_text_view.text = it
        }

        viewModel.privatbankSelectedCurrency.observe(this) {
            privatbankExchangeRatesAdapter.setSelectedCurrency(it)
        }

        viewModel.nbuSelectedCurrency.observe(this) {
            nbuExchangeRatesAdapter.setSelectedCurrency(it)
            nbu_exchange_rates_recycler_view.scrollToPosition(it)
        }

        viewModel.privatbankExchangeRates.observe(this) {
            privatbankExchangeRatesAdapter.setData(it)
        }

        viewModel.nbuExchangeRates.observe(this) {
            nbuExchangeRatesAdapter.setData(it)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        if (calendar.time <= Calendar.getInstance().time) {
            viewModel.onDateSet(calendar.time)
        } else {
            Toast.makeText(this, getString(R.string.exchange_rates_still_not_generated), Toast.LENGTH_LONG).show()
        }
    }

    override fun onPrivatbankCurrencySelect(currency: String) {
        viewModel.onPrivatbankCurrencySelect(currency)
    }
}