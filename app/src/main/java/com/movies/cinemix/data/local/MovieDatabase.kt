package com.movies.cinemix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.model.LastMovies

@Database(entities = [MovieDetails::class, LastMovies::class], version = 12)
@TypeConverters(MoviesTypeConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val lastMoviesDao: LastMoviesDao
}