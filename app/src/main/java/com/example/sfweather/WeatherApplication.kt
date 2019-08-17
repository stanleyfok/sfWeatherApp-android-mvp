package com.example.sfweather

import android.app.Application
import androidx.room.Room
import com.example.sfweather.databases.AppDB
import com.example.sfweather.repositories.SearchHistoryRepository
import com.example.sfweather.services.SearchHistoryService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDB::class.java,
            AppDB.DB_NAME
        ).build()

        val appModule = module {
            single { database }
            single { get<AppDB>().searchHistoryDao() }
            single { SearchHistoryService() }
            single { SearchHistoryRepository() }
        }

        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModule)
        }
    }
}
