package com.tzel.movieflix.ui.movie.moviedetail.model

import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.domain.movie.entity.Movie
import javax.inject.Inject

class MovieDetailsUiToMovieMapper @Inject constructor() : Mapper {
    operator fun invoke(movieDetailsUi: MovieDetailsUi): Movie {
        return Movie(
            id = movieDetailsUi.id,
            title = movieDetailsUi.title,
            backdropPath = movieDetailsUi.imageUrl,
            posterPath = movieDetailsUi.imageUrl,
            isFavorite = !movieDetailsUi.isFavorite,
            releaseDate = null,
            genresIds = emptyList(),
        )
    }
}