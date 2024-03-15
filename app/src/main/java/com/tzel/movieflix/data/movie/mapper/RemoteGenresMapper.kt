package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteGenre
import com.tzel.movieflix.domain.movie.entity.Genre
import javax.inject.Inject

class RemoteGenresMapper @Inject constructor() {
    operator fun invoke(remoteGenre: List<RemoteGenre?>?): List<Genre> {
        return remoteGenre?.mapNotNull {
            if (it?.id == null || it.name == null) return@mapNotNull null
            Genre(it.id, it.name)
        } ?: emptyList()
    }
}