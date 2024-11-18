package com.tzel.movieflix.data.configuration

import com.tzel.movieflix.data.configuration.mapper.LanguageToLocalLanguageMapper
import com.tzel.movieflix.data.configuration.mapper.LocalLanguageToLanguageMapper
import com.tzel.movieflix.data.configuration.mapper.RemoteAvailableLanguagesMapper
import com.tzel.movieflix.data.configuration.mapper.RemoteTemporaryTokenToTemporaryTokenMapper
import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.configuration.entity.Language
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val dataSource: ConfigurationDataSource,
    private val remoteAvailableLanguagesMapper: RemoteAvailableLanguagesMapper,
    private val languageToLocalLanguageMapper: LanguageToLocalLanguageMapper,
    private val localLanguageToLanguageMapper: LocalLanguageToLanguageMapper,
    private val remoteTemporaryTokenToTemporaryTokenMapper: RemoteTemporaryTokenToTemporaryTokenMapper
) : ConfigurationRepository {
    override suspend fun initConfiguration() {
        dataSource.initConfiguration()
    }

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
        return DEFAULT_LANGUAGE
    }

    override suspend fun getTemporaryToken(): String? {
        val remoteToken = dataSource.requestTemporaryRequestToken() ?: return null
        val localValidToken = remoteTemporaryTokenToTemporaryTokenMapper(remoteToken) ?: return null
        return localValidToken
    }

    override suspend fun createAccessToken(requestToken: String): Boolean {
        val accessTokenResponse = dataSource.createAccessToken(requestToken) ?: return false

        if (accessTokenResponse.accessToken == null || accessTokenResponse.accountId == null || accessTokenResponse.success != true) return false

        dataSource.saveAccessTokenAndAccountId(accessTokenResponse.accessToken, accessTokenResponse.accountId)

        return true
    }

    override suspend fun getSavedAccountId(): String? {
        return dataSource.getAccount()?.accountId
    }

    companion object {
        private val DEFAULT_LANGUAGE = Language("en", "English", name = "English")
    }
}