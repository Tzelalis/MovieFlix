package com.tzel.movieflix.framework.user

import com.tzel.movieflix.data.user.UserDataSource
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val api: UserApi) : UserDataSource {
    override fun rateMovie(movieId: String, rate: Double) {

    }
}