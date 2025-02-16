package com.movies.cinemix.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.movies.cinemix.domain.repository.MoviesRepository
import com.movies.cinemix.data.remote.MoviesApi
import com.movies.cinemix.data.remote.MoviesPaging
import com.movies.cinemix.domain.model.Cast
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override fun getNowPlayingMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging{page -> moviesApi.getNowPlayingMovies(page)}
            }
        ).flow



    }

    override fun getPopularMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging{page -> moviesApi.getPopularMovies(page)}
            }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging{page -> moviesApi.getTopRatedMovies(page)}
            }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging{page -> moviesApi.getUpcomingMovies(page)}
            }
        ).flow
    }

    override suspend fun getMovieCrew(movieId: Int): CastResponse {
        return try {
            moviesApi.getMovieCrew(movieId = movieId)
        }catch (e :Exception){
            Log.v("Movies",e.message.toString())
            throw e
        }

    }


}