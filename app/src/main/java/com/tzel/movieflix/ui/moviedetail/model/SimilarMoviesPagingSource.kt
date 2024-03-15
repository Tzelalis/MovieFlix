package com.tzel.movieflix.ui.moviedetail.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tzel.movieflix.ui.moviedetail.mapper.SimilarMoviesUiMapper
import com.tzel.movieflix.usecase.movie.GetSimilarMoviesUseCase
import timber.log.Timber
import javax.inject.Inject

class SimilarMoviesPagingSource @Inject constructor(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val similarMoviesUiMapper: SimilarMoviesUiMapper
) : PagingSource<Int, SimilarMovieUiItem>() {

    var movieId: String = ""

    fun updateMovie(movieId: String) {
        this.movieId = movieId
    }
    override fun getRefreshKey(state: PagingState<Int, SimilarMovieUiItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimilarMovieUiItem> {
        return try {
            val currentPage = params.key ?: 1
            val result = getSimilarMoviesUseCase(movieId, currentPage) ?: return LoadResult.Error(Exception("No movies found"))
            val movies = similarMoviesUiMapper(result.movies, result.page)
            LoadResult.Page(
                data = movies,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < result.totalPages) currentPage + 1 else null
            )
        } catch (e: Exception) {
            Timber.tag(SimilarMoviesPagingSource::class.java.simpleName).e(e)
            return LoadResult.Error(e)
        }
    }
}