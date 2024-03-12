package com.tzel.movieflix.data.movie

import com.tzel.movieflix.domain.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource) : MovieRepository {
}