package com.movies.cinemix.domain.usecases.movies

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPersonMovies(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(personId: Int): Flow<PagingData<Movie>> {
        return moviesRepository.getPersonMovies(personId)
    }
}