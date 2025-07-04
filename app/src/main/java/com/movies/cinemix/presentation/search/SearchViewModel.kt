package com.movies.cinemix.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
):ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent){
        when (event){
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
                searchMovie()
            }
            is SearchEvent.SearchMovie -> {
                searchMovie()
            }
        }
    }

    private fun searchMovie() {
        if (state.value.searchQuery.isNotEmpty()){
            val movies = moviesUseCases.searchMovie(
                movieName = state.value.searchQuery
            ).cachedIn(viewModelScope)
            _state.value = _state.value.copy(moviesList = movies)
        }

    }

}