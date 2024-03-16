package com.tzel.movieflix.ui.movie.moviedetail.mapper

import com.tzel.movieflix.R
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieUiStats
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class MovieStatsUiMapper @Inject constructor() {
    operator fun invoke(releaseDate: String?, runtime: Int?): List<MovieUiStats> {
        val stats = mutableListOf<MovieUiStats>()

        releaseDate?.let {
            val formattedDate = mapReleaseDateFormat(it) ?: return@let
            stats.add(
                MovieUiStats(
                    icon = R.drawable.ic_calendar,
                    label = formattedDate,
                    contentDescription = null
                )
            )
        }

        runtime?.let {
            stats.add(
                MovieUiStats(
                    icon = R.drawable.ic_clock,
                    label = it.toString(),
                    contentDescription = null
                )
            )
        }

        return stats
    }

    private fun mapReleaseDateFormat(releaseDate: String): String? {
        val date = SimpleDateFormat(DATE_API_FORMAT, Locale.getDefault()).parse(releaseDate) ?: return null
        return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)
    }

    companion object {
        private const val DATE_API_FORMAT = "yyyy-MM-dd"
        private const val DATE_FORMAT = "dd MMM yyyy"

    }
}