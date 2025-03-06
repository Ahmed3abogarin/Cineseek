package com.movies.cinemix.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
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

    fun getMovies() {
        val popularMovies = moviesUseCases.getPopularMovies.invoke().cachedIn(viewModelScope)
        val topRatedMovies = moviesUseCases.getTopRatedMovies.invoke().cachedIn(viewModelScope)
        val upcomingMovies = moviesUseCases.getUpcomingMovies.invoke().cachedIn(viewModelScope)
        val trendWeek = moviesUseCases.getTrendWeek.invoke().cachedIn(viewModelScope)
        val nowPlayingMovies = moviesUseCases.getNowPlayingMovies.invoke().cachedIn(viewModelScope)
        val arabicMovies = moviesUseCases.getArabicMovies.invoke().cachedIn(viewModelScope)
        val marvelMovies = moviesUseCases.getMarvelMovies.invoke().cachedIn(viewModelScope)


//        val actionMovies = moviesUseCases.getGenreMovies.invoke(genreNum = 28).cachedIn(viewModelScope)
//        val ss = moviesUseCases.getGenreMovies.invoke(genreNum = 28).cachedIn(viewModelScope)

        _state.value = _state.value.copy(nowPlaying = nowPlayingMovies)
        _state.value = _state.value.copy(popularMovies = popularMovies)
        _state.value = _state.value.copy(topRatedMovies = topRatedMovies)
        _state.value = _state.value.copy(upcomingMovies = upcomingMovies)
        _state.value = _state.value.copy(trendWeek = trendWeek)
        _state.value = _state.value.copy(arabicMovies = arabicMovies)
        _state.value = _state.value.copy(marvelMovies = marvelMovies)

    }


}