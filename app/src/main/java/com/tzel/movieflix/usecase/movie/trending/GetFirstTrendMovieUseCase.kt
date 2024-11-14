package com.tzel.movieflix.usecase.movie.trending

import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import com.tzel.movieflix.usecase.movie.GetMovieDetailsUseCase
import javax.inject.Inject

class GetFirstTrendMovieUseCase @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) {
    suspend operator fun invoke(): MovieDetails? {
        val movie = getTrendingMoviesUseCase(timeWindow = TimeWindow.Day, page = 1)?.movies?.firstOrNull() ?: return null
        return getMovieDetailsUseCase(movie.id, includeImages = true, includeVideos = true)
    }
}