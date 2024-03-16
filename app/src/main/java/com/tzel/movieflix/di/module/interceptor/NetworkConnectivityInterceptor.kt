package com.tzel.movieflix.di.module.interceptor

import com.tzel.movieflix.data.core.NetworkManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectivityInterceptor @Inject constructor(private val networkManager: NetworkManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkManager.isNetworkAvailable()) throw NoNetworkException()
        return chain.proceed(chain.request())
    }
}

class NoNetworkException : IOException(NetworkManager.NO_NETWORK_ERROR_MESSAGE)