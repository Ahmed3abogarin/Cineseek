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

    @Query("SELECT movieId FROM LastMovies")
    fun getLastMovies(): Flow<List<Int>>

    @Query("DELETE FROM LastMovies WHERE id NOT IN (SELECT id FROM LastMovies ORDER BY id DESC LIMIT 10)")
    suspend fun deleteOldEntries()
}