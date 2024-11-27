package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.ConfigurationDataSource
import com.tzel.movieflix.data.configuration.model.LocalAccount
import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.utils.ext.requireNotNull
import javax.inject.Inject

class ConfigurationDataSourceImpl @Inject constructor(
    private val api: ConfigurationApi,
    private val dao: ConfigurationDao,
) : ConfigurationDataSource {
    override suspend fun initConfiguration() {
        dao.insertOrIgnoreConfiguration()
    }

    override suspend fun getAvailableLanguages(): List<RemoteLanguage?> {
        return api.getAvailableLanguages().requireNotNull()
    }

    override suspend fun setSaveLanguage(language: LocalLanguage) {
        return dao.saveLanguage(code = language.code, name = language.name, nameEn = language.englishName)
    }

    override suspend fun getSavedLanguage(): LocalLanguage? {
        return dao.getConfiguration()?.language
    }

    override suspend fun saveAccountDetails(account: LocalAccount) {

    }

    override suspend fun getAccount(): LocalAccount? {
        return dao.getConfiguration()?.user
    }
}