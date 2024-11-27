package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.RemoteLanguage
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.domain.core.Mapper
import javax.inject.Inject

class RemoteAvailableLanguagesMapper @Inject constructor() : Mapper {
    operator fun invoke(response: List<RemoteLanguage?>?): List<Language> {
        return response?.mapNotNull { invoke(it) } ?: emptyList()
    }

    private operator fun invoke(remoteLanguage: RemoteLanguage?): Language? {
        if (remoteLanguage?.code == null || remoteLanguage.englishName == null) return null

        return Language(
            code = remoteLanguage.code,
            englishName = remoteLanguage.englishName,
            name = remoteLanguage.name
        )
    }
}