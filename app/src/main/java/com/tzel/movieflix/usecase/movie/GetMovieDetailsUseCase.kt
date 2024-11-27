package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repo: MovieRepository) : UseCase {
    suspend operator fun invoke(
        movieId: String,
        includeCast: Boolean = false,
        includeImages: Boolean = false,
        includeVideos: Boolean = false,
        includeProviders: Boolean = false,
    ): MovieDetails? {
        return try {
            repo.getMovieDetails(
                movieId = movieId,
                includeCast = includeCast,
                includeImages = includeImages,
                includeVideos = includeVideos,
                includeProviders  = includeProviders,
            )
        } catch (e: Exception) {
            Timber.tag(GetMovieDetailsUseCase::class.java.simpleName).e(e)
            null
        }
    }
}