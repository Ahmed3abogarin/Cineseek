package com.movies.cinemix.presentation.search

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val moviesList: Flow<PagingData<Movies>>? = null
)