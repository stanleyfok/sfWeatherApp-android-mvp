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
import com.example.sfweather.features.weatherHistory.adapters.WeatherHistoryRecycleViewInterface
import com.example.sfweather.models.SearchHistory

import kotlinx.android.synthetic.main.fragment_weather_history.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent

class WeatherHistoryFragment : Fragment(), WeatherHistoryView, WeatherHistoryRecycleViewInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var presenter: WeatherHistoryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_history, container, false)
        this.presenter = WeatherHistoryPresenter(this);

        val self = this
        GlobalScope.launch(Dispatchers.Main) {
            val searchHistories = presenter.fetchAllSearchHistories()

            viewAdapter = WeatherHistoryRecycleViewAdapter(searchHistories, self)

            recyclerView = view.weatherHistoryRecycleView.apply {
                setHasFixedSize(true)
                adapter = viewAdapter
            }
        }

        return view
    }

    override fun onListFragmentInteraction(item: SearchHistory) {
        targetFragment?.let {
            val intent = Intent(context, WeatherHistoryFragment::class.java)
            intent.putExtra("cityId", item.cityId);

            it.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent);
        }

        (activity as MainActivity).popStack()
    }
}
