package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken
import javax.inject.Inject

class RemoteTemporaryTokenToTemporaryTokenMapper @Inject constructor() {
    operator fun invoke(remoteToken: RemoteTemporaryRequestToken?): String? {
        if (remoteToken?.token == null || remoteToken.success != true || remoteToken.statusCode?.toIntOrNull() != 1) return null

        return remoteToken.token
    }
}