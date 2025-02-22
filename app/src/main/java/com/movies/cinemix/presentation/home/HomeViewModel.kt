package com.movies.cinemix.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,

) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state



    init {
        getMovies()
    }

    fun getMovies(){
        val popularMovies = moviesUseCases.getPopularMovies.invoke()
        val topRatedMovies = moviesUseCases.getTopRatedMovies.invoke()
        val upcomingMovies = moviesUseCases.getUpcomingMovies.invoke()
        val trendWeek = moviesUseCases.getTrendWeek.invoke()
        val nowPlayingMovies = moviesUseCases.getNowPlayingMovies.invoke()

        _state.value = _state.value.copy(nowPlaying = nowPlayingMovies)
        _state.value = _state.value.copy(popularMovies = popularMovies)
        _state.value = _state.value.copy(topRatedMovies = topRatedMovies)
        _state.value = _state.value.copy(upcomingMovies = upcomingMovies)
        _state.value = _state.value.copy(trendWeek = trendWeek)

    }




}