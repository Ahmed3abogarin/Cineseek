package com.movies.cinemix.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickerViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
): ViewModel() {

    private val _state = mutableStateOf<Movies?>(null)
    val state = _state
    fun getRandomMovies(){
        viewModelScope.launch {
            _state.value = null
            delay(5000)
            val page = (1..500).random()
            val list = moviesUseCases.getRandomMovie(page)?.results
            if (list != null){
                _state.value = list.random()
            }
        }
    }
}