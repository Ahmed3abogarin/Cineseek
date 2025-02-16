package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.repository.MoviesRepository

class GetMovieCast(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): CastResponse{
        return moviesRepository.getMovieCrew(movieId)
    }
}