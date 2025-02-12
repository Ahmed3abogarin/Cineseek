package com.movies.cinemix.domain

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Result
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetNowPlayingMovies(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Result>>{
        return moviesRepository.getNowPlayingMovies()

    }
}