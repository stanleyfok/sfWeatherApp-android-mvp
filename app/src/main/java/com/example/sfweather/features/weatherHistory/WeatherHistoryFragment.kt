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

import android.content.Intent
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.fragment_weather_history.*
import pl.kitek.rvswipetodelete.SwipeToDeleteCallback

class WeatherHistoryFragment : Fragment(), WeatherHistoryContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: WeatherHistoryRecycleViewAdapter
    private lateinit var presenter: WeatherHistoryContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_history, container, false)
        this.presenter = WeatherHistoryPresenter(this);

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.fetchAllSearchHistories()
    }

        override fun onItemViewClick(searchHistory: SearchHistory) {
        targetFragment?.let {
            val intent = Intent(context, WeatherHistoryFragment::class.java)
            intent.putExtra("cityId", searchHistory.cityId);

            it.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent);
        }

        (activity as MainActivity).popStack()
    }

    override fun onItemViewSwipe(searchHistory: SearchHistory) {
        presenter.deleteSearchHistory(searchHistory)
    }

    override fun updateView(searchHistories: List<SearchHistory>) {
        val list = searchHistories.toMutableList()
        viewAdapter = WeatherHistoryRecycleViewAdapter(list, this)

        recyclerView = this.weatherHistoryRecycleView.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }

        val swipeHandler = object : SwipeToDeleteCallback(activity!!.applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as WeatherHistoryRecycleViewAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
