package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.LocalTemporaryRequestToken
import com.tzel.movieflix.domain.configuration.entity.TemporaryRequestToken
import javax.inject.Inject

class TemporaryTokenToLocalTemporaryTokenMapper @Inject constructor() {
    operator fun invoke(localToken: LocalTemporaryRequestToken?): TemporaryRequestToken? {
        if (localToken?.requestToken == null) return null

        return TemporaryRequestToken(
            requestToken = localToken.requestToken,
            expiresAt = localToken.expiresAt
        )
    }
}