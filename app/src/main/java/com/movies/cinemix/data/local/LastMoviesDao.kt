package com.movies.cinemix.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.cinemix.domain.model.LastMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface LastMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(movieId: LastMovies)

    @Query("SELECT * FROM LastMovies")
    fun getLastMovies(): Flow<List<Int>>
}