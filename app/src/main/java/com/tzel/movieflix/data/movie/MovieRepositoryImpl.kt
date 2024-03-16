package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.mapper.RemoteGenresMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieDetailsMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieMapper
import com.tzel.movieflix.data.movie.mapper.RemoteReviewsMapper
import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.Genre
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    private val remoteMovieMapper: RemoteMovieMapper,
    private val remoteMovieDetailsMapper: RemoteMovieDetailsMapper,
    private val remoteReviewsMapper: RemoteReviewsMapper,
    private val remoteGenresMapper: RemoteGenresMapper,
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): MovieResult {
        return try {
            remoteMovieMapper(dataSource.getPopularMovies(page))
        }catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetails {
        return remoteMovieDetailsMapper(dataSource.getMovieDetails(movieId))
    }

    override suspend fun getMovieReviews(movieId: String, page: Int): ReviewsResult {
        return remoteReviewsMapper(dataSource.getMovieReviews(movieId, page))
    }

    override suspend fun getSimilarMovies(movieId: String, page: Int): MovieResult {
        return remoteMovieMapper(dataSource.getSimilarMovies(movieId, page))
    }

    override suspend fun getGenres(): List<Genre> {
        return remoteGenresMapper(dataSource.getGenres().genres)
    }

    override suspend fun getMoviesByGenre(genreId: String, page: Int): MovieResult {
        return remoteMovieMapper(dataSource.getMoviesByGenre(genreId, page))
    }
}