package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.data.movie.model.RemoteGenresResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun getLocalPopularMovies(page: Int, language: String?): RemoteMovieResponse

    suspend fun getMovieDetails(
        movieId: String,
        includeCast: Boolean,
        includeImages: Boolean,
        includeVideos: Boolean,
        includeProviders: Boolean,
        language: String?
    ): RemoteMovieDetailsResponse

    suspend fun getSimilarMovies(movieId: String, page: Int): RemoteMovieResponse

    suspend fun getMovieReviews(movieId: String, page: Int): RemoteReviewsResponse

    suspend fun getGenres(language: String?): RemoteGenresResponse

    suspend fun getMoviesByGenre(genreId: String, page: Int): RemoteMovieResponse

    suspend fun getLocalMovies(): List<LocalMovie>

    fun getMovieFavoriteStatus(movieId: Long): Flow<Boolean>

    fun getLocalPopularMovies(): Flow<List<LocalMovie>>

    suspend fun deleteLocalMovies(vararg movie: LocalMovie)

    suspend fun saveLocalMovies(vararg movies: LocalMovie)

    suspend fun searchMovies(title: String, page: Int, language: String?): RemoteMovieResponse

    suspend fun upcomingMovies(page: Int, language: String?): RemoteMovieResponse

    suspend fun getTrendingMovies(timeWindow: TimeWindow, page: Int, language: String?): RemoteMovieResponse
}