package com.noxpa.exchangeratestask.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.noxpa.exchangeratestask.R
import com.noxpa.exchangeratestask.model.PrivatbankExchangeRate
import kotlinx.android.synthetic.main.item_privatbank_exchange_rate.view.*
import java.text.DecimalFormat

class PrivatbankExchangeRatesAdapter(
    private val listener: OnPrivatbankCurrencySelectListener,
    private var privatbankExchangeRates: List<PrivatbankExchangeRate> = mutableListOf(),
    private var selectedCurrency: String = ""
) : RecyclerView.Adapter<PrivatbankExchangeRatesAdapter.PrivatbankExchangeRatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivatbankExchangeRatesViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_privatbank_exchange_rate, parent, false)

        return PrivatbankExchangeRatesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrivatbankExchangeRatesViewHolder, position: Int) {
        holder.bind(privatbankExchangeRates[position], selectedCurrency, listener)
    }

    override fun getItemCount(): Int = privatbankExchangeRates.size

    class PrivatbankExchangeRatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            privatbankExchangeRate: PrivatbankExchangeRate,
            selectedCurrency: String,
            listener: OnPrivatbankCurrencySelectListener
        ) {
            setCurrency(privatbankExchangeRate.currency)
            setPurchaseRate(privatbankExchangeRate.purchaseRate)
            setSaleRate(privatbankExchangeRate.saleRate)
            setBackground(privatbankExchangeRate.currency, selectedCurrency)

            initClicks(privatbankExchangeRate.currency, listener)
        }

        private fun setCurrency(currency: String) {
            itemView.currency_text_view.text = currency
        }

        private fun setPurchaseRate(purchaseRate: Double) {
            itemView.purchase_rate_text_view.text = DecimalFormat("0.00").format(purchaseRate)
        }

        private fun setSaleRate(saleRate: Double) {
            itemView.sale_rate_text_view.text = DecimalFormat("0.00").format(saleRate)
        }

        private fun setBackground(currencyTextCode: String, selectedCurrency: String) {
            if (currencyTextCode == selectedCurrency) {
                itemView.rootView.background = getDrawable(R.drawable.item_privatbank_exchange_rate_selected_background)
            } else {
                itemView.rootView.background = getDrawable(R.drawable.item_privatbank_exchange_rate_white_background)
            }
        }

        private fun getDrawable(resource: Int) = ContextCompat.getDrawable(itemView.context, resource)

        private fun initClicks(currency: String, listener: OnPrivatbankCurrencySelectListener) {
            itemView.rootView.setOnClickListener { listener.onPrivatbankCurrencySelect(currency) }
        }
    }

    fun setData(privatbankExchangeRates: List<PrivatbankExchangeRate>) {
        this.privatbankExchangeRates = privatbankExchangeRates
        notifyDataSetChanged()
    }

    fun setSelectedCurrency(selectedCurrency: String) {
        this.selectedCurrency = selectedCurrency
        notifyDataSetChanged()
    }

    interface OnPrivatbankCurrencySelectListener {
        fun onPrivatbankCurrencySelect(currency: String)
    }
}