package com.movies.cinemix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movies.cinemix.domain.model.Movies

@Database(entities = [Movies::class], version = 2)
@TypeConverters(MoviesTypeConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val moviesDao: MoviesDao
}