package com.movies.cinemix.presentation.home

import androidx.lifecycle.ViewModel
import com.movies.cinemix.domain.GetNowPlayingMovies
import com.movies.cinemix.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {
    val nowPlayingMovies = GetNowPlayingMovies(moviesRepository).invoke()




    private var _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()


}