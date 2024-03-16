package com.tzel.movieflix.data.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tzel.movieflix.data.movie.model.LocalMovie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<LocalMovie>

    @Query("SELECT * FROM movies WHERE is_favorite == 1")
    fun getFavorites(): List<LocalMovie>

    @Insert
    fun insert(vararg movies: LocalMovie)

    @Delete
    fun delete(vararg movies: LocalMovie)

    @Update
    fun update(vararg movies: LocalMovie)
}
