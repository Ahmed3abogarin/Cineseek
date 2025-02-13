package com.movies.cinemix.presentation.home

import androidx.lifecycle.ViewModel
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    moviesUseCases: MoviesUseCases,
) : ViewModel() {
    val nowPlayingMovies = moviesUseCases.getNowPlayingMovies.invoke()
}