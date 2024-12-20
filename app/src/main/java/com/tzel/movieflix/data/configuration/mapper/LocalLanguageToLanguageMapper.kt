package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.domain.core.Mapper
import javax.inject.Inject

class LocalLanguageToLanguageMapper @Inject constructor() : Mapper {
    operator fun invoke(localLanguage: LocalLanguage?): Language? {
        if (localLanguage?.code == null || localLanguage.englishName == null) return null
        return Language(
            localLanguage.code,
            localLanguage.englishName,
            localLanguage.name
        )
    }
}