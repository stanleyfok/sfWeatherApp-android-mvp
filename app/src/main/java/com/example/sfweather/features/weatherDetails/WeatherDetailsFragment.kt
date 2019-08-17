package com.example.sfweather.features.weatherDetails

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.sfweather.MainActivity
import com.example.sfweather.R
import com.example.sfweather.features.weatherHistory.WeatherHistoryFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import android.content.Intent
import android.widget.ProgressBar
import com.example.sfweather.constants.AppConstants
import com.example.sfweather.utils.WeatherUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherDetailsFragment : Fragment(), WeatherDetailsView, View.OnClickListener, SearchView.OnQueryTextListener {
    private lateinit var presenter: WeatherDetailsPresenter
    private var state:WeatherDetailsState? = null
    private var cityIdToFetch:Int = -1

    //region life cycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_detail, container, false)

        this.presenter = WeatherDetailsPresenter(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewHistoryButton.setOnClickListener(this)
        searchView.setOnQueryTextListener(this)

        if (this.state != null) {
            this.updateView()

            if (this.cityIdToFetch != -1) {
                this.presenter.fetchWeatherByCityId(cityIdToFetch)

                this.cityIdToFetch = -1;
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                presenter.fetchLastStoredWeather()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.REQ_CODE_FRAGMENT_SEARCH_HISTORY) {

                // bad code, however onActivityResult starts before onViewCreated
                // store to use in onViewCreated
                this.cityIdToFetch = data!!.getIntExtra("cityId", -1)
            }
        }
    }
    //endregion

    //region click listener
    override fun onClick(view: View) {
        when (view.id) {
            R.id.viewHistoryButton -> {
                val fragment = WeatherHistoryFragment()
                fragment.setTargetFragment(this, AppConstants.REQ_CODE_FRAGMENT_SEARCH_HISTORY)

                (activity as MainActivity).replaceFragments(fragment)
            }
        }
    }
    //endregion

    //region searchView listener
    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (p0 != null && p0.isNotEmpty()) {
            this.presenter.fetchWeatherByCityName(p0)
            this.searchView.setQuery("", false)
        }

        this.searchView.clearFocus()

        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }
    //endregion

    //region interface methods
    override fun setState(state: WeatherDetailsState) {
        this.state = state

        this.updateView()
    }

    override fun setIsLoading(bool: Boolean) {
        this.progressBar.visibility = if (bool) ProgressBar.VISIBLE else ProgressBar.INVISIBLE
    }

    override fun updateView() {
        this.state?.let {
            val displayTemp = String.format("%.1f", WeatherUtils.kelvinToCelsius(it.temperature)) + "°"

            this.cityNameLabel.text = it.cityName
            this.temperatureLabel.text = displayTemp
            this.weatherLabel.text = it.weatherDesc
        }

    }

    override fun showErrorMessage(errorMessage: String) {
        Snackbar.make(this.view!!, errorMessage, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
