package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import javax.inject.Inject

class RemoteMovieDetailsMapper @Inject constructor(
    private val remoteCastMapper: RemoteCastMapper,
    private val mapGenres: RemoteGenresMapper
) {
    operator fun invoke(response: RemoteMovieDetailsResponse): MovieDetails {
        if (response.id == null) throw IllegalArgumentException("Movie id cannot be null")

        return with(response) {
            MovieDetails(
                id = response.id,
                title = title ?: originalTitle,
                posterPath = posterPath,
                backdropPath = backdropPath,
                releaseDate = releaseDate,
                adult = adult ?: false,
                genres = mapGenres(genres),
                popularity = popularity,
                overview = overview,
                runtime = runtime,
                budget = budget,
                status = status,
                tagline = tagline,
                voteAverage = voteAverage,
                voteCount = voteCount,
                cast = remoteCastMapper(credits?.cast),
                homepage = homepage
            )
        }
    }
}