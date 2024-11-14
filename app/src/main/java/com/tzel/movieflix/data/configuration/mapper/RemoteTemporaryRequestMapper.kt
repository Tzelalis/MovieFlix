package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.LocalTemporaryRequestToken
import com.tzel.movieflix.domain.configuration.entity.FailToCreateNewTemporaryRequestToken
import com.tzel.movieflix.domain.configuration.entity.TemporaryRequestToken
import javax.inject.Inject

class LocalTemporaryRequestTokenToTemporaryRequestTokenMapper @Inject constructor() {
    operator fun invoke(response: LocalTemporaryRequestToken?): TemporaryRequestToken {
        if(response?.requestToken == null) {
            throw FailToCreateNewTemporaryRequestToken
        }

        return TemporaryRequestToken(
            requestToken = response.requestToken,
            expiresAt = response.expiresAt
        )
    }
}