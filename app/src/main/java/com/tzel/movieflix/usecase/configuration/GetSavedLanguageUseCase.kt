package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.Language
import javax.inject.Inject

class GetSavedLanguageUseCase @Inject constructor(private val repo: ConfigurationRepository) {
    suspend operator fun invoke(): Language? {
        return repo.getSavedLanguage()
    }
}