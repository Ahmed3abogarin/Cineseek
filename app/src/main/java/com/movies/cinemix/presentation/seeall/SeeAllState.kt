package com.movies.cinemix.presentation.seeall

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow

data class SeeAllState(
    val movies: Flow<PagingData<Movies>>? = null
)