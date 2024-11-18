package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.model.LocalAccount
import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.data.configuration.model.RemoteAccessToken
import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.data.configuration.model.RemoteTemporaryRequestToken

interface ConfigurationDataSource {
    suspend fun initConfiguration()

    suspend fun getAvailableLanguages(): List<RemoteLanguage?>

    suspend fun setSaveLanguage(language: LocalLanguage)

    suspend fun getSavedLanguage(): LocalLanguage?

    suspend fun requestTemporaryRequestToken(): RemoteTemporaryRequestToken?

    suspend fun createAccessToken(requestToken: String): RemoteAccessToken?

    suspend fun saveAccessTokenAndAccountId(accessToken: String, accountId: String)

    suspend fun getAccount(): LocalAccount?
}