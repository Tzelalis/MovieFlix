package com.tzel.movieflix.data.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tzel.movieflix.data.movie.model.LocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<LocalMovie>

    @Query("SELECT * FROM movies WHERE movie_id == :movieId AND is_favorite == 1")
    fun getMovieFavoriteStatus(movieId: Long): Flow<List<LocalMovie>>

    @Query("SELECT * FROM movies WHERE is_popular == 1")
    fun getAllPopular(): Flow<List<LocalMovie>>

    @Insert
    suspend fun insert(vararg movies: LocalMovie)

    @Delete
    suspend fun delete(vararg movies: LocalMovie)

    @Update
    suspend fun update(vararg movies: LocalMovie)
}
