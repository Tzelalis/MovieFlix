package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import timber.log.Timber
import javax.inject.Inject

class RequestTemporaryRequestTokenUseCase @Inject constructor(private val repo: ConfigurationRepository) {
    suspend operator fun invoke(): String? {
        return try {
            repo.getTemporaryToken()
        } catch (e: Exception) {
            Timber.tag(RequestTemporaryRequestTokenUseCase::class.java.simpleName).e(e)
            return null
        }
    }
}