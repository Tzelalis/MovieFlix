package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import timber.log.Timber
import javax.inject.Inject

class GetSavedSessionUseCase @Inject constructor(private val repo: ConfigurationRepository) {
    suspend operator fun invoke(): String? {
        return try {
            repo.getSavedSessionId()
        } catch (e: Exception) {
            Timber.tag(GetSavedSessionUseCase::class.java.simpleName).e(e)
            null
        }
    }
}