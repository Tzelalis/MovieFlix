package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.RemoteLanguage

interface ConfigurationDataSource {
    suspend fun getAvailableLanguages(): List<RemoteLanguage?>

    suspend fun setSaveLanguage(language: LocalLanguage)

    suspend fun getSavedLanguage(): LocalLanguage?
}