package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository

class GetMovie(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(movieId:Int): Movies?{
        return moviesRepository.getMovie(movieId)
    }
}

// All the use cases use the movies repository