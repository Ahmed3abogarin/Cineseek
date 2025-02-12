package com.movies.cinemix.domain.repository

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Result
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

     fun getNowPlayingMovies(): Flow<PagingData<Result>>

}