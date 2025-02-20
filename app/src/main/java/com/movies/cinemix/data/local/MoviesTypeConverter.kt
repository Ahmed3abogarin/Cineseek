package com.movies.cinemix.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter


@ProvidedTypeConverter
class MoviesTypeConverter {
    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toIntList(data: String): List<Int> {
        return if (data.isEmpty()) emptyList() else data.split(",").map { it.toInt() }
    }
}