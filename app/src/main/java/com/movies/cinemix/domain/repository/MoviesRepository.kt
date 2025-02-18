package com.movies.cinemix.domain.repository

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.MovieKeyResponse
import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

     fun getNowPlayingMovies(): Flow<PagingData<Movies>>

     fun getPopularMovies(): Flow<PagingData<Movies>>

     fun getTopRatedMovies(): Flow<PagingData<Movies>>

     fun getUpcomingMovies(): Flow<PagingData<Movies>>

     fun getTrendingWeek(): Flow<PagingData<Movies>>

     suspend fun getMovieCrew(movieId: Int): CastResponse

     suspend fun getMovieKey(movieId: Int): MovieKeyResponse



}