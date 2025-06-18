package com.movies.cinemix.presentation.castdetails

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.model.PersonResponse
import kotlinx.coroutines.flow.Flow

data class CastState(
    val personInfo: PersonResponse? = null,
    val personMovies: Flow<PagingData<Movie>>? = null
)