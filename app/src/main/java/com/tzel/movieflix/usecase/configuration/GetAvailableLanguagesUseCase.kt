package com.tzel.movieflix.usecase.configuration

import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.usecase.core.UseCase
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor(private val repo: ConfigurationRepository) : UseCase {
    suspend operator fun invoke(
        systemLanguageCode: String,
    ): List<Language> {
        return try {
            repo.getAvailableLanguages()
                .sortedBy { it.englishName }
                .toMutableList()
                .apply {
                    removeIf { it.code == NO_LANGUAGE_CODE }

                    val systemLanguage = find { it.code == systemLanguageCode }
                    systemLanguage?.let { moveItemToTop(it) }

                    val englishLanguage = find { it.code == ENGLISH_CODE }
                    englishLanguage?.let { moveItemToTop(it) }
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun MutableList<Language>.moveItemToTop(item: Language) {
        remove(item)
        add(0, item)
    }

    companion object {
        private const val ENGLISH_CODE = "en"
        private const val NO_LANGUAGE_CODE = "xx"
    }
}