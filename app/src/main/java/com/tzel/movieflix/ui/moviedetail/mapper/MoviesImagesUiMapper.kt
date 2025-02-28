package com.tzel.movieflix.ui.moviedetail.mapper

import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.domain.movie.entity.MovieImageItem
import com.tzel.movieflix.domain.movie.entity.MovieImages
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import com.tzel.movieflix.ui.moviedetail.model.MovieImageUiItem
import com.tzel.movieflix.ui.moviedetail.model.MovieImagesUi
import javax.inject.Inject

class MoviesImagesUiMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) : Mapper {
    operator fun invoke(movieImages: MovieImages): MovieImagesUi {
        return MovieImagesUi(
            backdrops = movieImages.backdrops.mapNotNull { invoke(it) },
            posters = movieImages.posters.mapNotNull { invoke(it) }
        )
    }

    private fun invoke(image: MovieImageItem): MovieImageUiItem? {
        val url = imagePathMapper(image.filePath) ?: return null
        return MovieImageUiItem(
            imageUrl = url,
            aspectRatio = image.aspectRatio,
            height = image.height,
            width = image.width
        )
    }
}