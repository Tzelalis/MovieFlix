package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.domain.core.Mapper
import javax.inject.Inject

class LanguageToLocalLanguageMapper @Inject constructor() : Mapper {
    operator fun invoke(language: Language): LocalLanguage {
        return LocalLanguage(
            code = language.code,
            englishName = language.englishName,
            name = language.name
        )
    }
}