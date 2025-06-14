package com.movies.cinemix.presentation.random

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.model.Movies
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

    private val _state = mutableStateOf<Movies?>(null)
    val state = _state

    private val _key = mutableStateOf<String?>(null)
    val key = _key
    fun getRandomMovies() {
        Log.v("TAGY", "Get Random is getting called ")
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

    fun getMovieKey() {
        viewModelScope.launch {
            state.value?.let {
                val id = moviesUseCases.getMovieKey(it.id)
                id?.let {
                    if (it.results.isNotEmpty()){
                        _key.value = it.results.first().key
                    }

                }
            }
        }
    }
}