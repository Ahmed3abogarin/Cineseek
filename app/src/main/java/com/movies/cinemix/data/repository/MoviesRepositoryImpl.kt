package com.movies.cinemix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.movies.cinemix.domain.repository.MoviesRepository
import com.movies.cinemix.data.remote.MoviesApi
import com.movies.cinemix.data.remote.MoviesPaging
import com.movies.cinemix.domain.model.Result
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override fun getNowPlayingMovies(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging(
                    moviesApi = moviesApi
                )
            }
        ).flow



    }
}