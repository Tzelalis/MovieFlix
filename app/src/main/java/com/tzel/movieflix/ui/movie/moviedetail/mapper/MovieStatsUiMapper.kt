package com.tzel.movieflix.ui.movie.moviedetail.mapper

import com.tzel.movieflix.R
import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieUiStats
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class MovieStatsUiMapper @Inject constructor() : Mapper {
    operator fun invoke(releaseDate: String?, runtime: Int?, voteAverage: Double?): List<MovieUiStats> {
        val stats = mutableListOf<MovieUiStats>()

        if (!releaseDate.isNullOrBlank()) {
            mapReleaseDateFormat(releaseDate)?.let { formattedDate ->
                stats.add(
                    MovieUiStats(
                        icon = R.drawable.ic_calendar,
                        label = TextBuilder.Text(formattedDate),
                        contentDescription = TextBuilder.StringResource(R.string.home_details_release_date_content_description)
                    )
                )
            }
        }

        runtime?.let { time ->
            stats.add(
                MovieUiStats(
                    icon = R.drawable.ic_clock,
                    label = TextBuilder.Text(runtimeFormatting(time)),
                    contentDescription = TextBuilder.StringResource(R.string.home_details_runtime_content_description)
                )
            )
        }

        voteAverage?.let { vote ->
            stats.add(
                MovieUiStats(
                    icon = R.drawable.ic_star,
                    label = TextBuilder.StringResource(R.string.home_details_rating_label, voteFormatting(vote)),
                    contentDescription = TextBuilder.StringResource(R.string.home_details_rating_content_description)
                )
            )
        }

        return stats
    }

    private fun mapReleaseDateFormat(releaseDate: String): String? {
        val date = SimpleDateFormat(DATE_API_FORMAT, Locale.getDefault()).parse(releaseDate) ?: return null
        return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)
    }

    private fun voteFormatting(vote: Double): String {
        return String.format(Locale.getDefault(), "%.1f", vote)
    }

    private fun runtimeFormatting(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return if (hours > 0) {
            String.format(Locale.getDefault(), "%dh %02dm", hours, minutes)
        } else {
            String.format(Locale.getDefault(), "%dm", minutes)
        }
    }

    companion object {
        private const val DATE_API_FORMAT = "yyyy-MM-dd"
        private const val DATE_FORMAT = "dd MMM yyyy"

    }
}