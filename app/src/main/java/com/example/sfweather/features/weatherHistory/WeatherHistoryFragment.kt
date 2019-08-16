package com.example.sfweather.features.weatherHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sfweather.R
import com.example.sfweather.features.weatherHistory.adapters.WeatherHistoryRecycleViewAdapter

import kotlinx.android.synthetic.main.fragment_weather_history.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherHistoryFragment : Fragment(), WeatherHistoryView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var presenter: WeatherHistoryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_history, container, false)
        this.presenter = WeatherHistoryPresenter(this);

        GlobalScope.launch(Dispatchers.Main) {
            val searchHistories = presenter.fetchAllSearchHistories()

            viewAdapter = WeatherHistoryRecycleViewAdapter(searchHistories)

            recyclerView = view.weatherHistoryRecycleView.apply {
                setHasFixedSize(true)
                adapter = viewAdapter
            }
        }

        return view
    }
}
