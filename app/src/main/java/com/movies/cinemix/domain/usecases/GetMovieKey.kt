package com.movies.cinemix.domain.usecases

import com.movies.cinemix.domain.model.MovieKeyResponse
import com.movies.cinemix.domain.repository.MoviesRepository

class GetMovieKey(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId:Int): MovieKeyResponse{
        return moviesRepository.getMovieKey(movieId)
    }
}