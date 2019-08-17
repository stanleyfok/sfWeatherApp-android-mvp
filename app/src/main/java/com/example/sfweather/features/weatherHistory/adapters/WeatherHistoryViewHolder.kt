package com.example.sfweather.features.weatherHistory.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_history_item.view.*

class WeatherHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val cityNameLabel: TextView = itemView.cityNameLabel
    val dateLabel: TextView = itemView.dateLabel
}