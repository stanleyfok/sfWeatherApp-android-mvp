package com.example.sfweather.services

import com.example.sfweather.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class OWApiServiceInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val newUrl:HttpUrl = request.url.newBuilder().addQueryParameter("appId", BuildConfig.OPENWEATHER_API_TOKEN).build()

        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}