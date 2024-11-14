package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import timber.log.Timber
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(private val repo: ConfigurationRepository) {
    suspend operator fun invoke(token: String): Boolean {
        return try {
            repo.createSession(token)
            true
        } catch (e: Exception) {
            Timber.tag(CreateSessionUseCase::class.java.simpleName).e(e)
            false
        }
    }
}