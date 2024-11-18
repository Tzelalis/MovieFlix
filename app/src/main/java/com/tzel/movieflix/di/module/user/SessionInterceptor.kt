package com.tzel.movieflix.di.module.user

import com.tzel.movieflix.data.core.AppDatabase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SessionInterceptor @Inject constructor(private val db: AppDatabase) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val sessionId = runBlocking { db.configurationDao().getConfiguration()?.user?.accessToken } ?: return chain.proceed(original)

        val urlWithSession = originalUrl.newBuilder()
            .addQueryParameter("session_id", sessionId)
            .build()

        val requestWithSession = original.newBuilder()
            .url(urlWithSession)
            .build()

        return chain.proceed(requestWithSession)
    }
}