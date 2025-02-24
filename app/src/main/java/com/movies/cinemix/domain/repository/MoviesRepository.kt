package com.movies.cinemix.domain.repository

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.MovieKeyResponse
import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.model.PersonResponse
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getNowPlayingMovies(): Flow<PagingData<Movies>>

    fun getPopularMovies(): Flow<PagingData<Movies>>

    fun getTopRatedMovies(): Flow<PagingData<Movies>>

    fun getUpcomingMovies(): Flow<PagingData<Movies>>

    fun getTrendingWeek(): Flow<PagingData<Movies>>

    fun searchMovie(movieName: String): Flow<PagingData<Movies>>

    suspend fun getMovieCrew(movieId: Int): CastResponse

    suspend fun getMovieKey(movieId: Int): MovieKeyResponse

    // Room DB
    suspend fun upsertMovie(movie: Movies)

    suspend fun deleteMovie(movie: Movies)

    fun getMovies(): Flow<List<Movies>>

    fun getMovie(movieId: Int): Movies?

    suspend fun getPersonInfo(personId: Int): PersonResponse

    fun getPersonMovies(personId: Int): Flow<PagingData<Movies>>

    fun getArabicMovies(): Flow<PagingData<Movies>>


}