package com.example.sfweather.features.weatherHistory.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfweather.R
import com.example.sfweather.models.SearchHistory
import kotlinx.android.synthetic.main.weather_history_item.view.*


class WeatherHistoryRecycleViewAdapter(
    private val myDataset: List<SearchHistory>,
    private val mListener: WeatherHistoryRecycleViewInterface
) : RecyclerView.Adapter<WeatherHistoryRecycleViewAdapter.MyViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as SearchHistory

            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_history_item, parent, false) as View

        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = myDataset[position]
        holder.mIdView.text = item.cityName
        holder.mContentView.text = DateFormat.format("yyyy-MM-dd hh:mm:ss", item.timestamp * 1000L).toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount() = myDataset.size

    inner class MyViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.cityName
        val mContentView: TextView = mView.date
    }
}
