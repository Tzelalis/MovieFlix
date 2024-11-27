package com.tzel.movieflix.data.user.mapper

import com.tzel.movieflix.data.user.model.RemoteMovieStatesResponse
import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.domain.user.entity.MovieStates
import javax.inject.Inject

class RemoteMovieStatesMapper @Inject constructor() : Mapper {
    operator fun invoke(response: RemoteMovieStatesResponse): MovieStates {
        return MovieStates(
            id = response.id,
            favorite = response.favorite ?: false,
            watchlist = response.watchlist ?: false,
            rated = null
        )

    }
}