package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.model.LocalAccount
import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.RemoteLanguage

interface ConfigurationDataSource {
    suspend fun initConfiguration()

    suspend fun getAvailableLanguages(): List<RemoteLanguage?>

    suspend fun setSaveLanguage(language: LocalLanguage)

    suspend fun getSavedLanguage(): LocalLanguage?

    suspend fun saveAccountDetails(account: LocalAccount)

    suspend fun getAccount(): LocalAccount?
}