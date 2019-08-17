package com.example.sfweather.features.weatherHistory

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sfweather.MainActivity
import com.example.sfweather.R
import com.example.sfweather.features.weatherHistory.adapters.WeatherHistoryRecycleViewAdapter
import com.example.sfweather.models.SearchHistory

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent
import kotlinx.android.synthetic.main.fragment_weather_history.*

class WeatherHistoryFragment : Fragment(), WeatherHistoryContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var presenter: WeatherHistoryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_history, container, false)
        this.presenter = WeatherHistoryPresenter(this);

        GlobalScope.launch() {
            presenter.fetchAllSearchHistories()
        }

        return view
    }

    override fun onListFragmentInteraction(searchHistory: SearchHistory) {
        targetFragment?.let {
            val intent = Intent(context, WeatherHistoryFragment::class.java)
            intent.putExtra("cityId", searchHistory.cityId);

            it.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent);
        }

        (activity as MainActivity).popStack()
    }

    override fun updateView(searchHistories: List<SearchHistory>) {
        viewAdapter = WeatherHistoryRecycleViewAdapter(searchHistories, this)

        recyclerView = this.weatherHistoryRecycleView.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }
}
