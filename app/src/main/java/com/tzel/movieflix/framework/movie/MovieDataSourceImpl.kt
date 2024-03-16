package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.MovieDao
import com.tzel.movieflix.data.movie.MovieDataSource
import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.data.movie.model.RemoteGenresResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.utils.composable.api.requireNotNull
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: MovieApi,
    //private val dao: MovieDao,
) : MovieDataSource {
    override suspend fun getPopularMovies(page: Int): RemoteMovieResponse {
        return executeOn.background {
            api.fetchPopularMovies(page = page).requireNotNull()
        }
    }

    override suspend fun getMovieDetails(movieId: String): RemoteMovieDetailsResponse {
        return executeOn.background {
            api.fetchMovieDetails(movieId = movieId).requireNotNull()
        }
    }

    override suspend fun getSimilarMovies(movieId: String, page: Int): RemoteMovieResponse {
        return executeOn.background {
            api.fetchSimilarMovies(movieId, page).requireNotNull()
        }
    }

    override suspend fun getMovieReviews(movieId: String, page: Int): RemoteReviewsResponse {
        return executeOn.background {
            api.fetchMovieReviews(movieId, page).requireNotNull()
        }
    }

    override suspend fun getGenres(): RemoteGenresResponse {
        return executeOn.background {
            api.fetchGenres().requireNotNull()
        }
    }

    override suspend fun getMoviesByGenre(genreId: String, page: Int): RemoteMovieResponse {
        return executeOn.background {
            api.fetchMoviesByGenre(genreId, page).requireNotNull()
        }
    }

    override suspend fun getLocalMovies(): List<LocalMovie> {
        return executeOn.background {
            emptyList()
            //dao.getAll()
        }
    }

    override suspend fun getFavoriteMovies(): List<LocalMovie> {
        return executeOn.background {
            //dao.getFavorites()
            emptyList()
        }
    }

    override suspend fun deleteMovie(vararg movie: LocalMovie) {
        return executeOn.background {
            //dao.delete(*movie)
        }
    }

    override suspend fun saveMovie(vararg movie: LocalMovie) {
        return executeOn.background {
            //dao.insert(*movie)
        }
    }

    override suspend fun updateMovies(vararg movies: LocalMovie) {
        return executeOn.background {
            //dao.update(*movies)
        }
    }
}