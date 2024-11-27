package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteMovieImageItem
import com.tzel.movieflix.data.movie.model.RemoteMovieImages
import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.domain.movie.entity.MovieImageItem
import com.tzel.movieflix.domain.movie.entity.MovieImages
import javax.inject.Inject

class RemoteMovieImagesMapper @Inject constructor() : Mapper {
    operator fun invoke(remoteImages: RemoteMovieImages?): MovieImages? {
        if (remoteImages == null) return null

        return MovieImages(
            backdrops = remoteImages.backdrops?.mapNotNull { mapImage(it) } ?: emptyList(),
            posters = remoteImages.posters?.mapNotNull { mapImage(it) } ?: emptyList()
        )
    }

    private fun mapImage(remoteImage: RemoteMovieImageItem?): MovieImageItem? {
        if (remoteImage?.filePath == null) return null

        return MovieImageItem(
            filePath = remoteImage.filePath,
            aspectRatio = remoteImage.aspectRatio,
            height = remoteImage.height,
            width = remoteImage.width
        )
    }
}