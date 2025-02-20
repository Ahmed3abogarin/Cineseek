package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovies(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movies>>{
        return moviesRepository.getMovies()
    }
}