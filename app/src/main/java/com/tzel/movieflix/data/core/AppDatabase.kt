package com.tzel.movieflix.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tzel.movieflix.data.configuration.model.LocalConfiguration
import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.framework.configuration.ConfigurationDao
import com.tzel.movieflix.framework.movie.MovieDao

@Database(
    entities = [LocalMovie::class, LocalConfiguration::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun configurationDao(): ConfigurationDao
}
