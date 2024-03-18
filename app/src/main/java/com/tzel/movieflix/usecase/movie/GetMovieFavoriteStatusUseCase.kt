package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieFavoriteStatusUseCase @Inject constructor(private val repo: MovieRepository) {
    operator fun invoke(movieId: String): Flow<Boolean> {
        return repo.getMovieFavoriteStatus(movieId)
    }
}