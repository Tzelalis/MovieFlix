package com.tzel.movieflix.domain.configuration

import com.tzel.movieflix.domain.configuration.entity.Language

interface ConfigurationRepository {
    suspend fun initConfiguration()

    suspend fun getAvailableLanguages(): List<Language>

    suspend fun saveLanguage(language: Language)

    suspend fun getSavedLanguage(): Language?

    suspend fun getBackupLanguage(): Language

    suspend fun getSavedAccountId(): String?
}