package com.movies.cinemix.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Movies)

    @Delete
    fun delete(movie: Movies)

    @Query("SELECT * FROM Movies")
    fun getMovies(): Flow<List<Movies>>

    @Query("SELECT * FROM Movies WHERE id =:movieId")
    fun getMovie(movieId: Int): Movies?
}