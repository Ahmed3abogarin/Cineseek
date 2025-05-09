package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.SingleMovie
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository

class UpsertMovie(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: SingleMovie){
        moviesRepository.upsertMovie(movie)
    }
}