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

    private val _state = mutableStateOf(RandomMovieState())
    val state = _state

    init {
        getRandomMovie()
    }

    fun getRandomMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            val page = (1..500).random()
            val list = moviesUseCases.getRandomMovie(page)?.results
            if (!list.isNullOrEmpty()){
                delay(5000)
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(movie = list.random())
                }
            }else{
                _state.value = _state.value.copy(error = "Something went wrong :(")
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}

data class RandomMovieState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error : String? = null
)
