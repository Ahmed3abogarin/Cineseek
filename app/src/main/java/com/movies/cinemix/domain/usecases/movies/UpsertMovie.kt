package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.repository.MoviesRepository

class UpsertMovie(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: MovieDetails){
        moviesRepository.upsertMovie(movie)
    }
}