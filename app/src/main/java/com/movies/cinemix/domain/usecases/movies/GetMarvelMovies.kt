package com.movies.cinemix.domain.usecases.movies

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMarvelMovies(
    private val moviesRepository: MoviesRepository,
) {

    operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesRepository.getMarvelsMovies()
    }
}