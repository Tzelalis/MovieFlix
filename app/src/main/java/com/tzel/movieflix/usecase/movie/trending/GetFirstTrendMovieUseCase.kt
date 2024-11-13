package com.tzel.movieflix.usecase.movie.trending

import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import javax.inject.Inject

class GetFirstTrendMovieUseCase @Inject constructor(private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase) {
    suspend operator fun invoke(): Movie? {
        return getTrendingMoviesUseCase(timeWindow = TimeWindow.Day, page = 1)?.movies?.firstOrNull()
    }
}