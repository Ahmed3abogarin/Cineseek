package com.movies.cinemix.data.repository

import android.util.Log
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.repository.MoviesRepository
import com.movies.cinemix.data.remote.MoviesApi

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getNowPlayingMovies(): Movie? {
        return try{
            moviesApi.getNowPlayingMovies()
        }catch (e:Exception){
            Log.v("Movies",e.message.toString())
            return null
        }
    }
}