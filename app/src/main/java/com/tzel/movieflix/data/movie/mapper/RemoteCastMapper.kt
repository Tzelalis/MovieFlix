package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteCast
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import javax.inject.Inject

class RemoteCastMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) {
    operator fun invoke(remoteCast: List<RemoteCast?>?): List<Cast> {
        return remoteCast?.mapNotNull { mapCast(it) } ?: emptyList()
    }

    private fun mapCast(remoteCast: RemoteCast?): Cast? {
        return with(remoteCast) {
            if (this?.id == null) return@with null

            val name = name ?: originalName
            val imageUrl = imagePathMapper(profilePath)

            if (name == null && imageUrl == null) return@with null

            Cast(
                id = id,
                name = name,
                imageUrl = imageUrl
            )
        }
    }
}