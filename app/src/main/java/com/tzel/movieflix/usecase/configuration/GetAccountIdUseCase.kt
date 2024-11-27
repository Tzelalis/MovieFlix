package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetAccountIdUseCase @Inject constructor(private val repo: ConfigurationRepository) : UseCase {
    suspend operator fun invoke(): String? {
        return try {
            repo.getSavedAccountId()
        } catch (e: Exception) {
            Timber.tag(GetAccountIdUseCase::class.java.simpleName).e(e)
            null
        }
    }
}