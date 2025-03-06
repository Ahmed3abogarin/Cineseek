package com.movies.cinemix.domain.usecases.movies

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetUpcomingMovies(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movies>>{
        return moviesRepository.getUpcomingMovies()
    }
}