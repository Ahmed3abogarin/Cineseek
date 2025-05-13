package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.repository.MoviesRepository

class GetRandomMovie(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(page: Int): MovieResponse? {
        return moviesRepository.getRandomMovie(page)
    }
}