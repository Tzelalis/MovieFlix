package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.LocalTemporaryRequestToken
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken
import javax.inject.Inject

class RemoteTemporaryTokenToLocalTemporaryTokenMapper @Inject constructor() {
    operator fun invoke(remoteToken: RemoteTemporaryRequestToken?): LocalTemporaryRequestToken? {
        if (remoteToken?.token == null || remoteToken.success != true) return null

        return LocalTemporaryRequestToken(
            requestToken = remoteToken.token,
            expiresAt = remoteToken.expiresAt
        )
    }
}