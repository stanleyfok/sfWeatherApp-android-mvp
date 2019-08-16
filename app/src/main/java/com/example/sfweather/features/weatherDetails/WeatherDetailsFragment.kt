package com.example.sfweather.features.weatherDetails

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

class WeatherDetailsFragment : Fragment(), WeatherDetailsView, View.OnClickListener, SearchView.OnQueryTextListener {
    private lateinit var presenter: WeatherDetailsPresenter

    //region life cycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_detail, container, false)

        this.presenter = WeatherDetailsPresenter(this);

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewHistoryButton.setOnClickListener(this)
        this.searchView.setOnQueryTextListener(this)

        //TODO: load from storage
    }
    //endregion

    //region click listener
    override fun onClick(view: View) {
        when (view.id) {
            R.id.viewHistoryButton -> {
                (activity as MainActivity).replaceFragments(WeatherHistoryFragment())

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
    override fun updateTemperature(temp: String) {
        this.temperatureLabel.text = temp
    }

    override fun updateCityName(cityName: String) {
        this.cityNameLabel.text = cityName
    }

    override fun updateWeatherDescription(desc: String) {
        this.weatherLabel.text = desc
    }

    override fun showErrorMessage(errorMessage: String) {
        Snackbar.make(this.view!!, errorMessage, Snackbar.LENGTH_SHORT).show();
    }
    //endregion
}
