package com.movies.cinemix.presentation.home

import androidx.lifecycle.ViewModel
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,

) : ViewModel() {
    val nowPlayingMovies = moviesUseCases.getNowPlayingMovies.invoke()
    val popularMovies = moviesUseCases.getPopularMovies.invoke()
    val topRatedMovies = moviesUseCases.getTopRatedMovies.invoke()
    val upcomingMovies = moviesUseCases.getUpcomingMovies.invoke()
    val trendWeek = moviesUseCases.getTrendWeek.invoke()


}