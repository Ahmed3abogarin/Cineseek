package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.repository.MoviesRepository

class UpsertLastMovie(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int){
        moviesRepository.upsertLastMovie(movieId)
    }
}