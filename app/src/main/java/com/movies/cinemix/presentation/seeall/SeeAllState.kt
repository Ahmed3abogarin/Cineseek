package com.movies.cinemix.presentation.seeall

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class SeeAllState(
    val category: String? = null,
    val movies: Flow<PagingData<Movie>>? = null
)