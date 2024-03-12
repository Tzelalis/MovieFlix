package com.tzel.movieflix.framework.movie

import com.tzel.movieflix.data.movie.MovieDataSource
import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(private val executeOn: ExecuteOn) : MovieDataSource {
}