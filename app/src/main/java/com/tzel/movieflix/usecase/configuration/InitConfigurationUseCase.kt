package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import timber.log.Timber
import javax.inject.Inject

class InitConfigurationUseCase @Inject constructor(private val repo: ConfigurationRepository) {
    suspend operator fun invoke() {
        try {
            repo.initConfiguration()
        } catch (e: Exception) {
            Timber.tag(InitConfigurationUseCase::class.java.simpleName).e(e)
        }
    }
}