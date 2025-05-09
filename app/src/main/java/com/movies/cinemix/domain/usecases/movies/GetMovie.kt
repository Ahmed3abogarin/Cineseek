package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.SingleMovie
import com.movies.cinemix.domain.repository.MoviesRepository

class GetMovie(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(movieId:Int): SingleMovie?{
        return moviesRepository.getMovie(movieId)
    }
}

// All the use cases use the movies repository