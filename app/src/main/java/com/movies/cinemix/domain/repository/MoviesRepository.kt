package com.movies.cinemix.domain.repository

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.MovieKeyResponse
import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.model.PersonResponse
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getTrendingWeek(): Flow<PagingData<Movie>>

    fun getGenreMovies(genreNum: Int): Flow<PagingData<Movie>>

    fun getMarvelsMovies(): Flow<PagingData<Movie>>

    fun searchMovie(movieName: String): Flow<PagingData<Movie>>

    suspend fun getMovieCrew(movieId: Int): CastResponse?

    suspend fun getMovieKey(movieId: Int): MovieKeyResponse?

    // Room DB
    suspend fun upsertMovie(movie: MovieDetails)

    suspend fun deleteMovie(movie: MovieDetails)

    fun getMovies(): Flow<List<MovieDetails>>

    fun getMovie(movieId: Int): MovieDetails?

    suspend fun getPersonInfo(personId: Int): PersonResponse

    fun getPersonMovies(personId: Int): Flow<PagingData<Movie>>

    fun getArabicMovies(): Flow<PagingData<Movie>>

    suspend fun upsertLastMovie(movieId: Int)
    suspend fun getLastMovies() : List<MovieDetails>

    suspend fun getMovieById(movieId: Int): MovieDetails

    suspend fun getRandomMovie(page: Int):MovieResponse?

}