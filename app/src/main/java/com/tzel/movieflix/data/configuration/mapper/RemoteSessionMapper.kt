package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.RemoteSession
import javax.inject.Inject

class RemoteSessionMapper @Inject constructor() {
    operator fun invoke(response: RemoteSession?): String? {
        if(response?.sessionId == null || response.success != true) return null

        return response.sessionId
    }
}