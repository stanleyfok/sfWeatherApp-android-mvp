package com.example.sfweather.fragments.weatherDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sfweather.MainActivity
import com.example.sfweather.R
import com.example.sfweather.fragments.weatherHistory.WeatherHistoryFragment
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import kotlinx.android.synthetic.main.fragment_weather_detail.view.*
import kotlin.math.roundToInt

class WeatherDetailsFragment : Fragment(), View.OnClickListener, WeatherDetailsView {

    private lateinit var presenter: WeatherDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_weather_detail, container, false)
        view.viewHistoryButton.setOnClickListener(this)

        this.presenter = WeatherDetailsPresenter(this);

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.fetchWeatherByCityName("London")
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.viewHistoryButton -> {
                (activity as MainActivity).replaceFragments(WeatherHistoryFragment())
                //Toast.makeText(this.activity, "Clicked on Button", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun updateTemperature(temp: String) {
        this.temperatureLabel.text = temp
    }

    override fun updateCityName(cityName: String) {
        this.cityNameLabel.text = cityName
    }

    override fun updateWeatherDescription(desc: String) {
        this.weatherLabel.text = desc
    }
}
