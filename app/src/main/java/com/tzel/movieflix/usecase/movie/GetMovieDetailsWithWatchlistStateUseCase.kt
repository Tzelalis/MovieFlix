package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.usecase.core.UseCase
import com.tzel.movieflix.usecase.user.GetMovieStatesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetMovieDetailsWithWatchlistStateUseCase @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieStatesUseCase: GetMovieStatesUseCase,
) : UseCase {
    suspend operator fun invoke(movieId: String): MovieDetails? {
        return coroutineScope {
            val movieDetailsDeferred = async {
                getMovieDetailsUseCase(
                    movieId = movieId,
                    includeCast = true,
                    includeVideos = true,
                    includeProviders = true
                )
            }
            val watchlistStateDeferred = async { getMovieStatesUseCase(movieId) }

            val movieDetails = movieDetailsDeferred.await()
            val watchlist = watchlistStateDeferred.await()
            movieDetails?.copy(inWatchlist = watchlist?.watchlist ?: false)
        }
    }
}