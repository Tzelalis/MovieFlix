package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteGenre
import javax.inject.Inject

class RemoteGenresMapper @Inject constructor() {
    operator fun invoke(remoteGenre: List<RemoteGenre?>?): List<String> {
        return remoteGenre?.mapNotNull { it?.name } ?: emptyList()
    }
}