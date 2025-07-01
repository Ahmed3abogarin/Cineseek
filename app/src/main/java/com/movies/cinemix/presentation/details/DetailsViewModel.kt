package com.movies.cinemix.presentation.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state


    var sideEffect by mutableStateOf<String?>(null)
        private set // this mean it can not be changed out side this fucking class


    init {
        savedStateHandle.get<String>("movie_id")?.let {
            getMovieById(it.toInt())
            getMovieKey(it.toInt())
            check(it.toInt())
        }
    }

    private fun getMovieById(id: Int) {
        viewModelScope.launch {
            val movie = moviesUseCases.getMovieById(id)
            _state.value = _state.value.copy(movie = movie)
            getMovieCast(id)
            addMovieToLast(movie?.id)
        }

    }

    private suspend fun addMovieToLast(movieId: Int?) {
        movieId?.let {
            moviesUseCases.upsertLastMovie(movieId )
        }

    }

    private suspend fun getMovieCast(movieId: Int) {
        try {
            val movies = moviesUseCases.getMovieCast.invoke(movieId = movieId)
            _state.value = state.value.copy(castList = movies)

        } catch (e: Exception) {
            Log.v("ERROR", e.message.toString())
        }


    }

    private fun getMovieKey(movieId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val movieKey = moviesUseCases.getMovieKey.invoke(movieId = movieId)
                movieKey?.let {
                    if (movieKey.results.isNotEmpty()) {
                        _state.value = state.value.copy(movieKey = movieKey.results[0].key)
                    }
                }
            }
        } catch (e: Exception) {
            Log.v("ERROR", e.message.toString())
        }

    }

    fun onEvent(event: DetailsEvent) {
        when (event) {

            is DetailsEvent.SaveDeleteMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val movie = moviesUseCases.getMovie(movieId = event.movie.id)
                    if (movie == null) {
                        upsertMovie(event.movie)
                    } else {
                        deleteMovie(event.movie)
                    }
                }
            }

            is DetailsEvent.AddLastMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    moviesUseCases.upsertLastMovie(event.movieId)
                }
            }

            is DetailsEvent.CheckSaveStatus -> {
                check(event.movieId)
            }

            is DetailsEvent.CheckTrailerStatus -> {
                viewModelScope.launch {
                    sideEffect = "Trailer unavailable"
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null

            }
        }
    }

    private fun check(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = moviesUseCases.getMovie(movieId = movieId) != null
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(savedStatus = isSaved)
            }
        }
    }

    private fun deleteMovie(movie: MovieDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesUseCases.deleteMovie(movie)
            sideEffect = "Movie deleted :("
            check(movie.id)
        }

    }

    private fun upsertMovie(movie: MovieDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesUseCases.upsertMovie(movie)
            sideEffect = "Movie Saved"
            check(movie.id)

        }
    }
}