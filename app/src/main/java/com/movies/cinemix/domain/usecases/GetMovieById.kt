package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.repository.MoviesRepository

class GetMovieById(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(moviesId: Int): MovieDetails? {
        return moviesRepository.getMovieById(moviesId)
    }
}