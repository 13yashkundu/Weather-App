package com.example.weatherz_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class ForecastItem(
    val day: String,
    val temp: String,
    val iconUrl: String = ""
)

class ForecastAdapter(
    private val forecastList: List<ForecastItem>
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tempText: TextView = itemView.findViewById(R.id.forecast_weather)
        val iconImage: ImageView = itemView.findViewById(R.id.weathr_icon_forecast)
        val dayText: TextView = itemView.findViewById(R.id.forecast_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_forecast_card, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastList[position]
        holder.tempText.text = item.temp
        holder.dayText.text = item.day

        if (item.iconUrl.isNotEmpty()) {
            val fullUrl = if (item.iconUrl.startsWith("//")) "https:${item.iconUrl}" else item.iconUrl
            Glide.with(holder.itemView.context)
                .load(fullUrl)
                .placeholder(R.drawable.night_icon)
                .into(holder.iconImage)
        } else {
            holder.iconImage.setImageResource(R.drawable.night_icon)
        }
    }

    override fun getItemCount(): Int = forecastList.size
}
