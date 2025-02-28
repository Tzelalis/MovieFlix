package com.tzel.movieflix.ui.dashboard.movie.home.model

import com.tzel.movieflix.domain.movie.entity.Genre
import java.text.SimpleDateFormat
import java.util.Locale

data class MovieUiItem(
    val id: String,
    val title: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val genres: List<Genre>,
    val key: String,
) {
    val releaseDateFormatted: String?
        get() {
            if (releaseDate.isNullOrEmpty()) return null

            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

            return try {
                val inputDate = inputFormat.parse(releaseDate) ?: return null
                outputFormat.format(inputDate)
            } catch (e: Exception) {
                null
            }
        }
}