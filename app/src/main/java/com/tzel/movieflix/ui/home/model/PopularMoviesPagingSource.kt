package com.tzel.movieflix.ui.home.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.ui.home.mapper.MovieUiMapper
import timber.log.Timber

class MoviesPagingSource(
    private val getMovies: suspend (Int) -> MovieResult?,
    private val movieUiMapper: MovieUiMapper
) : PagingSource<Int, MovieUiItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieUiItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUiItem> {
        return try {
            val currentPage = params.key ?: 1
            val result = getMovies(currentPage) ?: return LoadResult.Error(Exception("No movies found"))
            val movies = movieUiMapper(result.movies, result.page)
            LoadResult.Page(
                data = movies,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < result.totalPages) currentPage + 1 else null
            )
        } catch (e: Exception) {
            Timber.tag(MoviesPagingSource::class.java.simpleName).e(e)
            return LoadResult.Error(e)
        }
    }
}