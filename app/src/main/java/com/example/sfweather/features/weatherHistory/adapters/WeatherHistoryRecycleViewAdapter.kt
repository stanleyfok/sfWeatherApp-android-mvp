package com.example.sfweather.features.weatherHistory.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sfweather.R
import com.example.sfweather.features.weatherHistory.WeatherHistoryContract
import com.example.sfweather.models.SearchHistory

class WeatherHistoryRecycleViewAdapter(
    private val searchHistories: List<SearchHistory>,
    private val mListener: WeatherHistoryContract.View
) : RecyclerView.Adapter<WeatherHistoryViewHolder>() {
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val searchHistory = v.tag as SearchHistory

            mListener?.onListFragmentInteraction(searchHistory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryViewHolder {
        return WeatherHistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_history_item, parent, false)
        )
    }

    override fun getItemCount() = searchHistories.size

    override fun onBindViewHolder(holder: WeatherHistoryViewHolder, position: Int) {
        val searchHistory = searchHistories[position]

        holder.cityNameLabel.text = searchHistory.cityName
        holder.dateLabel.text = DateFormat.format("yyyy-MM-dd hh:mm:ss", searchHistory.timestamp * 1000L).toString()

        with(holder.itemView) {
            tag = searchHistory
            setOnClickListener(mOnClickListener)
        }
    }
}
