package com.example.sfweather.fragments.weatherDetails

import com.example.sfweather.models.OWApiError
import com.example.sfweather.models.OWResult
import com.example.sfweather.services.OWApiService
import com.example.sfweather.utils.WeatherUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class WeatherDetailsPresenter(_view: WeatherDetailsView) {

    private var view: WeatherDetailsView = _view
    private var owResult: OWResult? = null

    fun fetchWeatherByCityName(cityName: String) {
        val apiClient = OWApiService.create()
        apiClient.findByCityName(cityName).enqueue(object: Callback<OWResult> {
            override fun onResponse(call: Call<OWResult>, response: Response<OWResult>) {
                if (response.isSuccessful) {
                    owResult = response.body()!!

                    updateView()
                } else {
                    try {
                        val apiError = OWApiError.createFromResponse(response.errorBody()!!)

                        view.showErrorMessage(apiError.message)
                    } catch (e:Exception) {
                        view.showErrorMessage(if (!e.message.isNullOrEmpty()) e.message!! else "Unknown Error")
                    }
                }
            }

            override fun onFailure(call: Call<OWResult>, t: Throwable) {
                println(t.message)

                //TODO: more detailed error?
                view.showErrorMessage("Unknown Error")
            }
        })
    }

    private fun updateView() {
        this.owResult?.let { owResult ->
            view.updateCityName(owResult.name)

            val displayTemp = WeatherUtils.kelvinToCelsius(owResult.main.temp).roundToInt().toString() + "°"
            view.updateTemperature(displayTemp)

            if (owResult.weather.isNotEmpty()) {
                view.updateWeatherDescription(owResult.weather[0].main)
            }
        } ?: run {
            view.updateCityName("-")
            view.updateTemperature("-")
            view.updateWeatherDescription("-")
        }
    }
}