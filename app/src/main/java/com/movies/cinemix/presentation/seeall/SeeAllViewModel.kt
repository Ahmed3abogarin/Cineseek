package com.movies.cinemix.presentation.seeall

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SeeAllState())
    val state: State<SeeAllState> = _state

    init {
        savedStateHandle.get<String>("movie_category")?.let { category ->
            getMovies(category)
        }
    }

    fun getMovies(category: String){
        val list = when (category) {
            "nowPlaying" -> moviesUseCases.getNowPlayingMovies.invoke()
            "topRated" -> moviesUseCases.getTopRatedMovies.invoke()
            "upcoming" -> moviesUseCases.getUpcomingMovies.invoke()
            "popular" -> moviesUseCases.getPopularMovies.invoke()
            "arabic" -> moviesUseCases.getArabicMovies.invoke()
            "marvel" -> moviesUseCases.getMarvelMovies.invoke()
            "Action" -> moviesUseCases.getGenreMovies.invoke(genreNum = 28)
            "Adventure" -> moviesUseCases.getGenreMovies.invoke(genreNum = 12)
            "Family" -> moviesUseCases.getGenreMovies.invoke(genreNum = 10751)
            "Drama" -> moviesUseCases.getGenreMovies.invoke(genreNum = 18)
            "Animation" -> moviesUseCases.getGenreMovies.invoke(genreNum = 16)
            "Comedy" -> moviesUseCases.getGenreMovies.invoke(genreNum = 35)
            "Romance" -> moviesUseCases.getGenreMovies.invoke(genreNum = 10749)
            "Crime" -> moviesUseCases.getGenreMovies.invoke(genreNum = 80)
            "Horror" -> moviesUseCases.getGenreMovies.invoke(genreNum = 27)
            "War" -> moviesUseCases.getGenreMovies.invoke(genreNum = 10752)
            "Fantasy" -> moviesUseCases.getGenreMovies.invoke(genreNum = 14)
            else -> moviesUseCases.getPopularMovies.invoke()
        }.cachedIn(viewModelScope)
        _state.value = _state.value.copy(movies = list)
    }
}