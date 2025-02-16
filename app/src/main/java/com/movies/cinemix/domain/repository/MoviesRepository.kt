package com.movies.cinemix.domain.repository

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

     fun getNowPlayingMovies(): Flow<PagingData<Movies>>

     fun getPopularMovies(): Flow<PagingData<Movies>>

     fun getTopRatedMovies(): Flow<PagingData<Movies>>

     fun getUpcomingMovies(): Flow<PagingData<Movies>>

     suspend fun getMovieCrew(movieId: Int): CastResponse


}