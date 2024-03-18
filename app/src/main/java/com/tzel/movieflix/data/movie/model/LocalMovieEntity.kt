package com.tzel.movieflix.data.movie.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey @ColumnInfo(name = "movie_id") val id: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "last_name") val releaseDate: String?,
    @ColumnInfo(name = "backdrop_url") val backdropUrl: String?,
    @ColumnInfo(name = "poster_url") val posterUrl: String?,
    @ColumnInfo(name = "is_popular") val isPopular: Boolean,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
)