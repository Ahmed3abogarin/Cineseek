package com.movies.cinemix.domain.usecases

import com.movies.cinemix.SingleMovie
import com.movies.cinemix.domain.repository.MoviesRepository

class GetMovieById(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(moviesId: Int): SingleMovie {
        return moviesRepository.getMovieById(moviesId)
    }
}