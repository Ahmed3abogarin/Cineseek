package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetLastMovies(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<MovieDetails>> {
        return moviesRepository.getLastMovies()
    }
}