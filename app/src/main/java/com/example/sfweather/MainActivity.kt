package com.example.sfweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.sfweather.dummy.DummyContent
import com.example.sfweather.features.weatherDetails.WeatherDetailsFragment
import com.example.sfweather.features.weatherHistory.WeatherHistoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragContainer, WeatherDetailsFragment())
        ft.commit()
    }

    fun replaceFragments(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.slide_in_from_rigth, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        ft.addToBackStack("stack")
        ft.replace(R.id.fragContainer, fragment)
        ft.commit()
    }
}
