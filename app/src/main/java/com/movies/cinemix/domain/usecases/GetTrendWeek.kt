package com.movies.cinemix.domain.usecases

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetTrendWeek(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movies>>{
        return moviesRepository.getTrendingWeek()
    }
}