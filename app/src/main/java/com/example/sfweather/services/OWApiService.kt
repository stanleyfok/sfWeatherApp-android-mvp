package com.example.sfweather.services

import com.example.sfweather.BuildConfig
import com.example.sfweather.models.OWResult
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OWApiService {

    @GET("weather")
    fun findByCityName(@Query("q") cityName: String): Call<OWResult>

    @GET("weather")
    fun findByCityId(@Query("id") cityId: Int): Call<OWResult>

    companion object Factory {
        fun create(): OWApiService {
            val owServiceInterceptor = OWApiServiceInterceptor()

            val okHttpClient = OkHttpClient().newBuilder().
                addInterceptor(owServiceInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.OPENWEATHER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(OWApiService::class.java);
        }
    }
}