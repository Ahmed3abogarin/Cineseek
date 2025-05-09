package com.movies.cinemix.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.cinemix.SingleMovie
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: SingleMovie)

    @Delete
    fun delete(movie: SingleMovie)

    @Query("SELECT * FROM SingleMovie")
    fun getMovies(): Flow<List<SingleMovie>>

    @Query("SELECT * FROM SingleMovie WHERE id =:movieId")
    fun getMovie(movieId: Int): SingleMovie?
}