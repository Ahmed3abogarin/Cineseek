package com.movies.cinemix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movies.cinemix.SingleMovie
import com.movies.cinemix.domain.model.LastMovies

@Database(entities = [SingleMovie::class, LastMovies::class], version = 6)
@TypeConverters(MoviesTypeConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val lastMoviesDao: LastMoviesDao
}