package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.MovieDataSource
import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.data.movie.model.RemoteGenresResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieDetailsResponse
import com.tzel.movieflix.data.movie.model.RemoteMovieResponse
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import com.tzel.movieflix.utils.ext.requireNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val executeOn: ExecuteOn,
    private val api: MovieApi,
    private val dao: MovieDao,
) : MovieDataSource {
    override suspend fun getLocalPopularMovies(page: Int, language: String?): RemoteMovieResponse {
        return executeOn.background {
            api.fetchPopularMovies(page = page, language = language).requireNotNull()
        }
    }

    override suspend fun getMovieDetails(
        movieId: String,
        includeCast: Boolean,
        includeImages: Boolean,
        includeVideos: Boolean,
        includeProviders: Boolean,
        language: String?,
    ): RemoteMovieDetailsResponse {
        return executeOn.background {
            val includes = mutableListOf<String>().apply {
                if (includeCast) add("credits")
                if (includeProviders) add("watch/providers")
                if (includeImages) add("images")
                if (includeVideos) add("videos")
            }.joinToString(separator = ",")

            api.fetchMovieDetails(movieId = movieId, includes = includes, language = language).requireNotNull()
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

    override suspend fun getGenres(language: String?): RemoteGenresResponse {
        return executeOn.background {
            api.fetchGenres(language = language).requireNotNull()
        }
    }

    override suspend fun getMoviesByGenre(genreId: String, page: Int): RemoteMovieResponse {
        return executeOn.background {
            api.fetchMoviesByGenre(genreId, page).requireNotNull()
        }
    }

    override suspend fun getLocalMovies(): List<LocalMovie> {
        return executeOn.background {
            dao.getAll()
        }
    }

    override fun getLocalPopularMovies(): Flow<List<LocalMovie>> {
        return dao.getAllPopular()
    }

    override suspend fun deleteLocalMovies(vararg movie: LocalMovie) {
        return executeOn.background {
            dao.delete(*movie)
        }
    }

    override suspend fun saveLocalMovies(vararg movies: LocalMovie) {
        return executeOn.background {
            movies.forEach { movie ->
                try {
                    dao.insert(movie)
                } catch (e: Exception) {
                    dao.update(movie)
                }
            }
        }
    }

    override fun getMovieFavoriteStatus(movieId: Long): Flow<Boolean> {
        return dao.getMovieFavoriteStatus(movieId).map { it.isNotEmpty() }
    }

    override suspend fun searchMovies(title: String, page: Int, language: String?): RemoteMovieResponse {
        return executeOn.background {
            api.searchMovies(title = title, page = page, language = language).requireNotNull()
        }
    }

    override suspend fun upcomingMovies(page: Int, language: String?): RemoteMovieResponse {
        return executeOn.background {
            api.getUpcoming(page = page, language = language).requireNotNull()
        }
    }

    override suspend fun getTrendingMovies(timeWindow: TimeWindow, page: Int, language: String?): RemoteMovieResponse {
        val timeWindowKey = when (timeWindow) {
            TimeWindow.Day -> "day"
            TimeWindow.Week -> "week"
        }

        return executeOn.background {
            api.getTrending(timeWindow = timeWindowKey, page = page, language = language).requireNotNull()
        }
    }
}
