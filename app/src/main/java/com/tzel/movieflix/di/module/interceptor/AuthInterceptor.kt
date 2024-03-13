package com.tzel.movieflix.di.module.interceptor

import com.tzel.movieflix.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .build()
        return chain.proceed(request)
    }
}