package com.movies.cinemix.presentation.seeall

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
): ViewModel() {
    fun getMovies(category: String): Flow<PagingData<Movies>> {
        return when(category) {
            "nowPlaying" -> moviesUseCases.getNowPlayingMovies.invoke()
            "topRated" -> moviesUseCases.getTopRatedMovies.invoke()
            "upcoming" -> moviesUseCases.getUpcomingMovies.invoke()
            "popular" -> moviesUseCases.getPopularMovies.invoke()
            "arabic" -> moviesUseCases.getArabicMovies.invoke()
            else -> moviesUseCases.getPopularMovies.invoke() // default
        }
    }


}