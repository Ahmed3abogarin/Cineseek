package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovies(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<List<MovieDetails>>{
        return moviesRepository.getMovies()
    }
}