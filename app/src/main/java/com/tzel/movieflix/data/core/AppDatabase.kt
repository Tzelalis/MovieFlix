package com.tzel.movieflix.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tzel.movieflix.data.movie.MovieDao
import com.tzel.movieflix.data.movie.model.LocalGenre
import com.tzel.movieflix.data.movie.model.LocalMovie

@Database(entities = [LocalMovie::class, LocalGenre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
