package com.movies.cinemix.presentation.favorite

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteState())
    val state: State<FavoriteState> = _state

    init {
        getSavedMovies()
    }

    private fun getSavedMovies() {
        Log.v("COSETTEMOVIES", "get movies called")


        moviesUseCases.getMovies().onEach {
            _state.value = _state.value.copy(favoriteMovies = it.asReversed())
        }.launchIn(viewModelScope)

        // The problem was it just not launched in viewmodel scope

    }
}