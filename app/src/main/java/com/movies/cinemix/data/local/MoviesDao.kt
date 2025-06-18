package com.movies.cinemix.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.cinemix.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: MovieDetails)

    @Delete
    fun delete(movie: MovieDetails)

    @Query("SELECT * FROM MovieDetails")
    fun getMovies(): Flow<List<MovieDetails>>

    @Query("SELECT * FROM MovieDetails WHERE id =:movieId")
    fun getMovie(movieId: Int): MovieDetails?
}