package com.noxpa.exchangeratestask.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.noxpa.exchangeratestask.R
import com.noxpa.exchangeratestask.model.NbuExchangeRate
import kotlinx.android.synthetic.main.item_nbu_exchange_rate.view.*

class NbuExchangeRatesAdapter(
    private var nbuExchangeRates: List<NbuExchangeRate> = mutableListOf(),
    private var selectedCurrency: Int = -1
) : RecyclerView.Adapter<NbuExchangeRatesAdapter.NbuExchangeRatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NbuExchangeRatesViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_nbu_exchange_rate, parent, false)

        return NbuExchangeRatesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NbuExchangeRatesViewHolder, position: Int) {
        holder.bind(nbuExchangeRates, position, selectedCurrency)
    }

    override fun getItemCount(): Int = nbuExchangeRates.size

    class NbuExchangeRatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            nbuExchangeRates: List<NbuExchangeRate>,
            position: Int,
            selectedCurrency: Int
        ) {
            setCurrencyDescription(nbuExchangeRates[position].currencyDescription)
            setPurchaseRate(nbuExchangeRates[position].exchangeRate)
            setPerUnit(nbuExchangeRates[position].currencyTextCode)
            setBackground(position, selectedCurrency)
        }

        private fun setCurrencyDescription(currencyDescription: String) {
            itemView.currency_description_text_view.text = currencyDescription
        }

        private fun setPurchaseRate(exchangeRate: Double) {
            itemView.exchange_rate_text_view.text = exchangeRate.toString()
        }

        private fun setPerUnit(currencyTextCode: String) {
            itemView.per_unit_text_view.text = itemView.context.getString(R.string.per_unit, currencyTextCode)
        }

        private fun setBackground(position: Int, selectedCurrency: Int) {
            if (position % 2 != 0) {
                itemView.rootView.background = getDrawable(R.drawable.item_nbu_exchange_rate_dim_background)
            } else {
                itemView.rootView.background = getDrawable(R.drawable.item_nbu_exchange_rate_white_background)
            }

            if (position == selectedCurrency) {
                itemView.rootView.background = getDrawable(R.drawable.item_nbu_exchange_rate_selected_background)
            }
        }

        private fun getDrawable(resource: Int) = ContextCompat.getDrawable(itemView.context, resource)
    }

    fun setData(nbuExchangeRates: List<NbuExchangeRate>) {
        this.nbuExchangeRates = nbuExchangeRates
        notifyDataSetChanged()
    }

    fun setSelectedCurrency(selectedCurrency: Int) {
        this.selectedCurrency = selectedCurrency
        notifyDataSetChanged()
    }
}