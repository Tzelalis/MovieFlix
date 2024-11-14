package com.tzel.movieflix.data.user

import com.tzel.movieflix.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataSource: UserDataSource) : UserRepository {
    override fun rateMovie(movieId: String, rate: Double) {
        dataSource.rateMovie(movieId = movieId, rate = rate)
    }
}