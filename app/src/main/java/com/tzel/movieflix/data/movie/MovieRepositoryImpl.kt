package com.tzel.movieflix.data.movie

import com.tzel.movieflix.data.movie.mapper.LocalMoviesToMoviesMapper
import com.tzel.movieflix.data.movie.mapper.MoviesToLocalMoviesMapper
import com.tzel.movieflix.data.movie.mapper.RemoteGenresMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieDetailsMapper
import com.tzel.movieflix.data.movie.mapper.RemoteMovieMapper
import com.tzel.movieflix.data.movie.mapper.RemoteReviewsMapper
import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.Genre
import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import com.tzel.movieflix.domain.movie.entity.TimeWindow
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
    private val configurationRepository: ConfigurationRepository,
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): MovieResult {
        return try {
            val lang = configurationRepository.getSavedLanguage()?.code
            val popularMoviesResult = remoteMovieMapper(dataSource.getLocalPopularMovies(page = page, language = lang))
            val localMovies = localMoviesToMoviesMapper(dataSource.getLocalMovies())

            if (popularMoviesResult.page == 1) {
                val popularMoviesWithFavorite = popularMoviesResult.movies.map { movie ->
                    val isFavorite = localMovies.find { movie.id == it.id }?.isFavorite ?: false
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

    override suspend fun getMovieDetails(
        movieId: String,
        includeCast: Boolean,
        includeImages: Boolean,
        includeVideos: Boolean,
        includeProviders: Boolean,
    ): MovieDetails {
        val lang = configurationRepository.getSavedLanguage()?.code
        var details = remoteMovieDetailsMapper(
            dataSource.getMovieDetails(
                movieId = movieId,
                includeCast = includeCast,
                includeImages = includeImages,
                includeVideos = includeVideos,
                includeProviders = includeProviders,
                language = lang
            )
        )

        val infoMissing = details.overview.isNullOrBlank() || details.videos.isEmpty()
        if (infoMissing && lang != configurationRepository.getBackupLanguage().code) {
            val englishDetails = remoteMovieDetailsMapper(
                dataSource.getMovieDetails(
                    movieId = movieId,
                    includeCast = includeCast,
                    includeImages = includeImages,
                    includeVideos = includeVideos,
                    includeProviders = includeProviders,
                    language = null
                )
            )
            details = details.copy(
                overview = if (details.overview.isNullOrBlank()) englishDetails.overview else details.overview,
                videos = details.videos.ifEmpty { englishDetails.videos },
                homepage = if (details.homepage.isNullOrBlank()) englishDetails.homepage else details.homepage
            )
        }

        return details
    }

    override suspend fun getMovieReviews(movieId: String, page: Int): ReviewsResult {
        return remoteReviewsMapper(dataSource.getMovieReviews(movieId, page))
    }

    override suspend fun getSimilarMovies(movieId: String, page: Int): MovieResult {
        return remoteMovieMapper(dataSource.getSimilarMovies(movieId, page))
    }

    override suspend fun getGenres(): List<Genre> {
        val lang = configurationRepository.getSavedLanguage()?.code
        var genres = dataSource.getGenres(language = lang).genres

        if (genres.isNullOrEmpty() || genres.any { it?.name == null }) {
            genres = dataSource.getGenres(language = null).genres
        }
        return remoteGenresMapper(genres)
    }

    override suspend fun getMoviesByGenre(genreId: String, page: Int): MovieResult {
        return remoteMovieMapper(dataSource.getMoviesByGenre(genreId, page))
    }

    override suspend fun setFavoriteMovie(movie: Movie) {
        val localMovie = moviesToLocalMoviesMapper.mapMovie(movie = movie) ?: return

        dataSource.saveLocalMovies(localMovie)
    }

    override suspend fun searchMovies(query: String, page: Int): MovieResult {
        val lang = configurationRepository.getSavedLanguage()?.code
        return remoteMovieMapper(dataSource.searchMovies(title = query, page = page, language = lang))
    }

    override suspend fun upcomingMovies(page: Int): MovieResult {
        val lang = configurationRepository.getSavedLanguage()?.code
        return remoteMovieMapper(dataSource.upcomingMovies(page = page, language = lang))
    }

    override suspend fun getTrendingMovies(timeWindow: TimeWindow, page: Int): MovieResult {
        val lang = configurationRepository.getSavedLanguage()?.code
        return remoteMovieMapper(dataSource.getTrendingMovies(timeWindow, page, language = lang))
    }

    override fun getMovieFavoriteStatus(movieId: String): Flow<Boolean> {
        val id = movieId.toLongOrNull() ?: return flow { emit(false) }

        return dataSource.getMovieFavoriteStatus(id)
    }
}