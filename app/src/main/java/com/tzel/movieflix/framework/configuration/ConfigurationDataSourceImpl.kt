package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.ConfigurationDataSource
import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import javax.inject.Inject

class ConfigurationDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: ConfigurationApi,
    private val dao: ConfigurationDao,
) : ConfigurationDataSource {
    override suspend fun getAvailableLanguages(): List<RemoteLanguage?> {
        return executeOn.background {
            requireNotNull(api.getAvailableLanguages())
        }
    }

    override suspend fun setSaveLanguage(language: LocalLanguage) {
        return executeOn.background {
            dao.safeSaveLanguage(lang = language)
        }
    }

    override suspend fun getSavedLanguage(): LocalLanguage? {
        return dao.getConfiguration()?.language
    }
}