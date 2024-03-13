package com.tzel.movieflix.ui.home.model

import androidx.compose.ui.graphics.Color
import com.tzel.movieflix.ui.theme.GrayLight
import com.tzel.movieflix.ui.theme.RedMedium
import java.text.SimpleDateFormat
import java.util.Locale

data class MovieUiItem(
    val id: String,
    val title: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val adult: Boolean,
    val isFavorite: Boolean
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

    val favoriteIconColor: Color = if (isFavorite) RedMedium else GrayLight
}