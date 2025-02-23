package com.movies.cinemix.presentation

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.model.PersonResponse
import kotlinx.coroutines.flow.Flow

data class CastState(
    val personInfo: PersonResponse? = null,
    val personMovies: Flow<PagingData<Movies>>? = null
)