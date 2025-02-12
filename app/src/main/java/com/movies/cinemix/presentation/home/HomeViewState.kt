package com.movies.cinemix.presentation.home

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeViewState(
    val isLoading: Boolean = false,
    val nowPlayingMovies: LazyPagingItems<Result>? = null,
    val error: String? = null
)
