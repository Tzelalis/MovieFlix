package com.tzel.movieflix.framework.configuration

import com.tzel.movieflix.data.configuration.ConfigurationDataSource
import com.tzel.movieflix.data.configuration.model.LocalAccount
import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.RemoteAccessToken
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.data.configuration.model.RemoteSessionRequest
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.utils.ext.requireNotNull
import javax.inject.Inject

class ConfigurationDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: ConfigurationApi,
    private val dao: ConfigurationDao,
) : ConfigurationDataSource {
    override suspend fun initConfiguration() {
        dao.insertOrIgnoreConfiguration()
    }

    override suspend fun getAvailableLanguages(): List<RemoteLanguage?> {
        return executeOn.background {
            api.getAvailableLanguages().requireNotNull()
        }
    }

    override suspend fun setSaveLanguage(language: LocalLanguage) {
        return executeOn.background {
            dao.saveLanguage(code = language.code, name = language.name, nameEn = language.englishName)
        }
    }

    override suspend fun getSavedLanguage(): LocalLanguage? {
        return dao.getConfiguration()?.language
    }

    override suspend fun requestTemporaryRequestToken(): RemoteTemporaryRequestToken {
        return executeOn.background {
            api.requestTemporaryRequestToken().requireNotNull()
        }
    }

    override suspend fun createAccessToken(requestToken: String): RemoteAccessToken {
        return executeOn.background {
            api.createAccessToken(RemoteSessionRequest(requestToken)).requireNotNull()
        }
    }

    override suspend fun saveAccessTokenAndAccountId(accessToken: String, accountId: String) {
        dao.saveAccessTokenAndAccountId(accessToken = accessToken, accoundId = accountId)
    }

    override suspend fun getAccount(): LocalAccount? {
        return dao.getConfiguration()?.user
    }
}