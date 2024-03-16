package com.tzel.movieflix.data.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class NetworkManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun requireNetwork() {
        if (!isNetworkAvailable()) throw IOException(NO_NETWORK_ERROR_MESSAGE)
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    companion object {
        const val NO_NETWORK_ERROR_MESSAGE = "No network available"
    }
}

