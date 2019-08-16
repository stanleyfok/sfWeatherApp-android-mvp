package com.example.sfweather

import android.app.Application
import com.example.sfweather.services.SearchHistoryService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@WeatherApplication)

            val myModule = module {
                single { SearchHistoryService(applicationContext) }
            }

            // declare modules
            modules(myModule)
        }
    }
}
