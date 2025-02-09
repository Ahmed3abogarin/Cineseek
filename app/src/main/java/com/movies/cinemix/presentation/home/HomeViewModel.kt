package com.movies.cinemix.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {


    private var _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    init {
        getNowPlayingMovies()
    }


    fun getNowPlayingMovies(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val nowPlaying = moviesRepository.getNowPlayingMovies()
                _state.update { it.copy(nowPlayingMovies = nowPlaying) }
            }catch (e:Exception){
                Log.v("MOVIES",e.message.toString())
                _state.update { it.copy(error = e.message) }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}