package com.tzel.movieflix.di.module.interceptor

import com.tzel.movieflix.BuildConfig
import com.tzel.movieflix.data.core.AppDatabase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(private val db: AppDatabase) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val key = runBlocking { db.configurationDao().getConfiguration()?.user?.accessToken ?: BuildConfig.API_KEY }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $key")
            .build()

        return chain.proceed(request)
    }
}