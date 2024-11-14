package com.tzel.movieflix.data.configuration.mapper

import com.tzel.movieflix.data.configuration.model.LocalLanguage
import com.tzel.movieflix.domain.configuration.entity.Language
import javax.inject.Inject

class LanguageToLocalLanguageMapper @Inject constructor() {
    operator fun invoke(language: Language): LocalLanguage {
        return LocalLanguage(
            code = language.code,
            englishName = language.englishName,
            name = language.name
        )
    }
}