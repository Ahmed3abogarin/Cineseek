package com.movies.cinemix.domain.usecases

import com.movies.cinemix.SingleMovie
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetLastMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): List<SingleMovie>{
        return moviesRepository.getLastMovies()
    }
}