package com.tzel.movieflix.di.module.interceptor

import com.tzel.movieflix.data.core.AppDatabase
import com.tzel.movieflix.di.qualifier.SessionIdRequired
import com.tzel.movieflix.utils.ext.getAnnotation
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SessionInterceptor @Inject constructor(private val db: AppDatabase) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url

        request.getAnnotation(SessionIdRequired::class.java) ?: return chain.proceed(request)

        val sessionId = runBlocking { db.configurationDao().getConfiguration()?.user?.sessionId } ?: return chain.proceed(request)

        val urlWithSession = originalUrl.newBuilder()
            .addQueryParameter("session_id", sessionId)
            .build()

        val requestWithSession = request.newBuilder()
            .url(urlWithSession)
            .build()

        return chain.proceed(requestWithSession)
    }
}