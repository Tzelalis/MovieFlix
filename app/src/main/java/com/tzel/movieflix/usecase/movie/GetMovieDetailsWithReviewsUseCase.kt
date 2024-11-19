package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.usecase.user.GetMovieStatesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsWithReviewsUseCase @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieFavoriteStatusUseCase: GetMovieFavoriteStatusUseCase,
    private val getMovieStatesUseCase: GetMovieStatesUseCase,
) {
    operator fun invoke(movieId: String): Flow<MovieDetails?> {
        return flow {
            var movieDetails = getMovieDetailsUseCase(
                movieId = movieId,
                includeCast = true,
                includeVideos = true,
                includeProviders = true
            )

            getMovieStatesUseCase(movieId)?.let { watchlistState ->
                movieDetails = movieDetails?.copy(inWatchlist = watchlistState.watchlist)
            }

            emit(movieDetails)

            movieDetails?.let { details ->
                getMovieFavoriteStatusUseCase(movieId).firstOrNull()?.let { isFavorite ->
                    emit(details.copy(isFavorite = isFavorite))
                }
            }

        }.combine(getMovieFavoriteStatusUseCase(movieId)) { movieDetails, isFavorite ->
            movieDetails?.copy(isFavorite = isFavorite)
        }
    }
}