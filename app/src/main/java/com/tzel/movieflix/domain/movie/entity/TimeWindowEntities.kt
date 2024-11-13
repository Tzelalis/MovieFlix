package com.tzel.movieflix.domain.movie.entity

sealed class TimeWindow {
    data object Day : TimeWindow()
    data object Week : TimeWindow()
}