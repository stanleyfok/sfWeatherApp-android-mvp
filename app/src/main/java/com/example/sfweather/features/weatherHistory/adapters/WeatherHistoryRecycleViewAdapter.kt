package com.example.sfweather.features.weatherHistory.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfweather.R
import com.example.sfweather.features.weatherHistory.WeatherHistoryContract
import com.example.sfweather.models.SearchHistory
import kotlinx.android.synthetic.main.weather_history_item.view.*

class WeatherHistoryRecycleViewAdapter(
    private val searchHistories: MutableList<SearchHistory>,
    private val parentView: WeatherHistoryContract.View
) : RecyclerView.Adapter<WeatherHistoryRecycleViewAdapter.WeatherHistoryViewHolder>(), WeatherHistoryContract.Adapter {
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val searchHistory = v.tag as SearchHistory

            parentView.onItemViewClick(searchHistory)
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

    override fun removeAt(position: Int) {
        val searchHistory = searchHistories[position]
        parentView.onItemViewSwipe(searchHistory)

        searchHistories.removeAt(position)

        notifyItemRemoved(position)
    }

    inner class WeatherHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cityNameLabel: TextView = itemView.cityNameLabel
        val dateLabel: TextView = itemView.dateLabel
    }
}
