package com.tzel.movieflix.domain.movie

import com.tzel.movieflix.domain.movie.entity.Genre
import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): MovieResult

    suspend fun getMovieDetails(movieId: String): MovieDetails

    suspend fun getMovieReviews(movieId: String, page: Int): ReviewsResult

    suspend fun getSimilarMovies(movieId: String, page: Int): MovieResult

    suspend fun getGenres(): List<Genre>

    suspend fun getMoviesByGenre(genreId: String, page: Int): MovieResult

    suspend fun setFavoriteMovie(movie: Movie)

    fun getMovieFavoriteStatus(movieId: String): Flow<Boolean>
}