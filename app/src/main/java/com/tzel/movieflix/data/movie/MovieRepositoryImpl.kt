package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.mapper.LocalMoviesToMoviesMapper
import com.tzel.movieflix.data.movie.mapper.MoviesToLocalMoviesMapper
import com.tzel.movieflix.data.movie.mapper.RemoteGenresMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieDetailsMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieMapper
import com.tzel.movieflix.data.movie.mapper.RemoteReviewsMapper
import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.Genre
import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    private val remoteMovieMapper: RemoteMovieMapper,
    private val remoteMovieDetailsMapper: RemoteMovieDetailsMapper,
    private val remoteReviewsMapper: RemoteReviewsMapper,
    private val remoteGenresMapper: RemoteGenresMapper,
    private val localMoviesToMoviesMapper: LocalMoviesToMoviesMapper,
    private val moviesToLocalMoviesMapper: MoviesToLocalMoviesMapper,
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): MovieResult {
        return try {
            val popularMoviesResult = remoteMovieMapper(dataSource.getPopularMovies(page))
            val localMovies = localMoviesToMoviesMapper(dataSource.getLocalMovies())

            if (popularMoviesResult.page == 1) {
                val popularMoviesWithFavorite = popularMoviesResult.movies.map { movie ->
                    val isFavorite = localMovies.find { movie.id == it.id  }?.isFavorite ?: false
                    movie.copy(isFavorite = isFavorite)
                }
                val localMoviesWithFavorite = moviesToLocalMoviesMapper(popularMoviesWithFavorite, true)
                dataSource.saveLocalMovies(*localMoviesWithFavorite.toTypedArray())
            }

            popularMoviesResult
        } catch (e: Exception) {
            Timber.tag(MovieRepositoryImpl::class.java.simpleName).e(e)

            val localMovies = localMoviesToMoviesMapper(dataSource.getLocalMovies())
            MovieResult(
                page = 1,
                totalPages = 1,
                movies = localMovies,
                totalResults = localMovies.size
            )
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

    override suspend fun setFavoriteMovie(movie: Movie) {
        val localMovie = moviesToLocalMoviesMapper.mapMovie(movie = movie) ?: return

        dataSource.updateLocalMovies(localMovie)
    }

    override fun getMovieFavoriteStatus(movieId: String): Flow<Boolean> {
        val id = movieId.toLongOrNull() ?: return flow { emit(false) }

        return dataSource.getMovieFavoriteStatus(id)
    }
}