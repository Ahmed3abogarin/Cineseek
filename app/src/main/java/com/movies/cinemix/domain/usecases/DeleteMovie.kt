package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository

class DeleteMovie(
    private val moviesRepository: MoviesRepository
) {
     suspend operator fun invoke(movie:Movies){
        moviesRepository.deleteMovie(movie)
    }
}