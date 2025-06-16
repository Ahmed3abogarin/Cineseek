package com.movies.cinemix.presentation.seeall

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.movies.cinemix.domain.model.MovieCategory
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
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
            val movieCategory = MovieCategory.fromRoute(category)
            getMovies(movieCategory)
        }
    }

    fun getMovies(category: MovieCategory) {
        _state.value = _state.value.copy(category = category.title)

        val moviesFlow = when (category) {
            is MovieCategory.NowPlaying -> moviesUseCases.getNowPlayingMovies()
            is MovieCategory.TopRated -> moviesUseCases.getTopRatedMovies()
            is MovieCategory.Upcoming -> moviesUseCases.getUpcomingMovies()
            is MovieCategory.Popular -> moviesUseCases.getPopularMovies()
            is MovieCategory.Arabic -> moviesUseCases.getArabicMovies()
            is MovieCategory.Marvel -> moviesUseCases.getMarvelMovies()
            is MovieCategory.GenreCategory -> moviesUseCases.getGenreMovies(category.genreId)
        }.cachedIn(viewModelScope)

        _state.value = _state.value.copy(movies = moviesFlow)
    }

}