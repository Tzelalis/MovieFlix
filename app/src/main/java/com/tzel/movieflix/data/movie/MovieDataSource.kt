package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.data.movie.model.RemoteGenresResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse

interface MovieDataSource {
    suspend fun getPopularMovies(page: Int): RemoteMovieResponse

    suspend fun getMovieDetails(movieId: String): RemoteMovieDetailsResponse

    suspend fun getSimilarMovies(movieId: String, page: Int): RemoteMovieResponse

    suspend fun getMovieReviews(movieId: String, page: Int): RemoteReviewsResponse

    suspend fun getGenres(): RemoteGenresResponse

    suspend fun getMoviesByGenre(genreId: String, page: Int): RemoteMovieResponse

    suspend fun getLocalMovies(): List<LocalMovie>

    suspend fun getFavoriteMovies(): List<LocalMovie>

    suspend fun deleteMovie(vararg movie: LocalMovie)

    suspend fun saveMovie(vararg movie: LocalMovie)

    suspend fun updateMovies(vararg movies: LocalMovie)
}