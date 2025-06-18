package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.repository.MoviesRepository

class GetLastMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieDetails>{
        return moviesRepository.getLastMovies()
    }
}