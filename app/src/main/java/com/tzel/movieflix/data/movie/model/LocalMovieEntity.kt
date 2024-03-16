package com.tzel.movieflix.data.movie.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "last_name") val releaseDate: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @Embedded val genre: LocalGenre?,
    @ColumnInfo(name = "is_popular") val isPopular: Boolean,
    @ColumnInfo(name = "is_favorite") val isFavorite: String?,
)

@Entity(tableName = "genres")
data class LocalGenre(
    @PrimaryKey val id: String,
    val name: String?,
)

