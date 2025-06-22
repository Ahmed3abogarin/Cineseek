package com.movies.cinemix.presentation.random

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PickerViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf<Movie?>(null)
    val state = _state

    init {
        getRandomMovie()
    }

    fun getRandomMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = null
            val page = (1..500).random()
            val list = moviesUseCases.getRandomMovie(page)?.results
            withContext(Dispatchers.Main) {
                _state.value = null // if you want to show loading first
                delay(5000)
                _state.value = list?.random()

            }
        }
    }
}