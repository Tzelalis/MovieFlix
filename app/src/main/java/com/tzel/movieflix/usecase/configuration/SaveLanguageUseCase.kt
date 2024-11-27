package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.usecase.core.UseCase
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(private val repo: ConfigurationRepository) : UseCase {
    suspend operator fun invoke(lang: Language) {
        repo.saveLanguage(lang)
    }
}