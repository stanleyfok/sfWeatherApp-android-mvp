package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.common.services.SearchHistoryService
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherHistoryPresenter: WeatherHistoryContract.Presenter, KoinComponent {
    private var view:WeatherHistoryContract.View? = null
    private val searchHistoryService: SearchHistoryService by inject()

    private var searchHistories: MutableList<SearchHistory>? = null

    override fun attachView(view: WeatherHistoryContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onViewCreated() {
        CoroutineScope(Dispatchers.Main).launch {
            searchHistories = withContext(Dispatchers.IO) {
                searchHistoryService.getAll().toMutableList()
            }

            view?.reloadRecyclerView()
        }
    }

    override fun getAllSearchHistories(): List<SearchHistory>? {
        return this.searchHistories
    }

    override fun getSearchHistoryAtPosition(position: Int): SearchHistory? {
        this.searchHistories?.let {
            if (position < it.size) {
                return it[position]
            }
        }

        return null
    }

    override fun getSearchHistoryCount():Int {
        this.searchHistories?.let {
            return it.size
        }

        return 0
    }

    override fun selectSearchHistoryAtPosition(position: Int) {
        val searchHistory = this.getSearchHistoryAtPosition(position)

        if (searchHistory != null) {
            this.view?.onItemViewClick(searchHistory)
        }
    }

    override fun removeSearchHistoryAtPosition(position: Int) {
        val searchHistory = getSearchHistoryAtPosition(position)

        if (searchHistory != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val deleteCount = withContext(Dispatchers.IO) {
                    searchHistoryService.deleteByCityId(searchHistory.cityId)
                }

                if (deleteCount > 0) {
                    searchHistories!!.removeAt(position)

                    view?.reloadRecyclerView()
                }
            }
        }
    }
    //endregion
}