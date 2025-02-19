package com.movies.cinemix.domain.usecases

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMovie(
    private val  moviesRepository: MoviesRepository
) {
    operator fun invoke(movieName: String): Flow<PagingData<Movies>>{
        return moviesRepository.searchMovie(movieName)
    }
}