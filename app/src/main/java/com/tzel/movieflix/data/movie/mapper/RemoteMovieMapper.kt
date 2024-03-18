package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteMovie
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.domain.movie.entity.MovieResult
import javax.inject.Inject

class RemoteMovieMapper @Inject constructor() {
    operator fun invoke(response: RemoteMovieResponse): MovieResult {
        if (response.page == null || response.totalPages == null) throw IllegalArgumentException("Invalid response")

        return MovieResult(
            page = response.page,
            movies = response.movies?.mapNotNull { movie -> mapMovie(movie) } ?: emptyList(),
            totalPages = response.totalPages,
            totalResults = response.totalResults ?: 0
        )
    }

    private fun mapMovie(remoteMovie: RemoteMovie?): Movie? {
        if (remoteMovie?.id == null) return null

        return Movie(
            id = remoteMovie.id,
            title = remoteMovie.title,
            releaseDate = remoteMovie.releaseDate,
            backdropPath = remoteMovie.backdropPath,
            posterPath = remoteMovie.posterPath,
            genresIds = remoteMovie.genres?.mapNotNull { it } ?: emptyList(),
        )
    }
}