package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.mapper.LanguageToLocalLanguageMapper
import com.tzel.movieflix.data.configuration.mapper.LocalLanguageToLanguageMapper
import com.tzel.movieflix.data.configuration.mapper.RemoteAvailableLanguagesMapper
import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.Language
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val dataSource: ConfigurationDataSource,
    private val remoteAvailableLanguagesMapper: RemoteAvailableLanguagesMapper,
    private val languageToLocalLanguageMapper: LanguageToLocalLanguageMapper,
    private val localLanguageToLanguageMapper: LocalLanguageToLanguageMapper
) : ConfigurationRepository {
    override suspend fun getAvailableLanguages(): List<Language> {
        return remoteAvailableLanguagesMapper(dataSource.getAvailableLanguages())
    }

    override suspend fun saveLanguage(language: Language) {
        dataSource.setSaveLanguage(languageToLocalLanguageMapper(language))
    }

    override suspend fun getSavedLanguage(): Language? {
        return localLanguageToLanguageMapper(dataSource.getSavedLanguage())
    }

    override suspend fun getBackupLanguage(): Language {
        return Language("en", "English", name = "English")
    }
}