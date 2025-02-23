package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.repository.MoviesRepository

class GetPersonMovies(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(personId: Int): MovieResponse {
        return moviesRepository.getPersonMovies(personId)
    }
}