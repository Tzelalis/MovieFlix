package com.tzel.movieflix.framework.auth

import com.tzel.movieflix.domain.auth.AuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class AuthenticationBroadcast {

    private val _data = MutableStateFlow<AuthException?>(null)
    val data = _data.asStateFlow()

    fun updateData(e: AuthException) {
        _data.value = e
        Timber.tag(AuthenticationBroadcast::class.java.simpleName).v("AuthException: $e")
    }

    fun clearData() {
        _data.value = null
        Timber.tag(AuthenticationBroadcast::class.java.simpleName).v("clear")
    }
}