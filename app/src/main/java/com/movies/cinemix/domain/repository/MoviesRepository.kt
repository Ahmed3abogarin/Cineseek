package com.movies.cinemix.domain.repository

import com.movies.cinemix.domain.model.Movie


interface MoviesRepository {

    suspend fun getNowPlayingMovies(): Movie?

}