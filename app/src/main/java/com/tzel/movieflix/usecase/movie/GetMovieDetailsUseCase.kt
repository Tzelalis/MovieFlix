package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.usecase.core.UseCase
import com.tzel.movieflix.usecase.user.GetMovieStatesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repo: MovieRepository,
    private val getMovieStatesUseCase: GetMovieStatesUseCase
) : UseCase {
    suspend operator fun invoke(
        movieId: String,
        includeCast: Boolean = false,
        includeImages: Boolean = false,
        includeVideos: Boolean = false,
        includeProviders: Boolean = false,
        includeWatchlistState: Boolean = false,
    ): MovieDetails? {
        return coroutineScope {
            val movieDetailsDeferred = async {
                try {
                    repo.getMovieDetails(
                        movieId = movieId,
                        includeCast = includeCast,
                        includeImages = includeImages,
                        includeVideos = includeVideos,
                        includeProviders = includeProviders,
                    )
                } catch (e: Exception) {
                    Timber.tag(GetMovieDetailsUseCase::class.java.simpleName).e(e)
                    null
                }
            }

            val movieDetails = movieDetailsDeferred.await() ?: return@coroutineScope null

            if (!includeWatchlistState) {
                return@coroutineScope movieDetails
            }

            val watchlistState = async { getMovieStatesUseCase(movieId) }.await()
            movieDetails.copy(inWatchlist = watchlistState?.watchlist ?: false)
        }
    }
}