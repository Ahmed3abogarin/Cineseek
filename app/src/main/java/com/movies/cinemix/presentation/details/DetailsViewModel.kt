package com.movies.cinemix.presentation.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state


    var sideEffect by mutableStateOf<String?>(null)
        private set // this mean it can not be changed out side this fucking class


    fun openDialog() {
        _state.value = _state.value.copy(showDialog = true)
    }

    fun closeDialog() {
        _state.value = _state.value.copy(showDialog = false)
    }


    private fun getMovieCast(movieId: Int) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val movies = moviesUseCases.getMovieCast.invoke(movieId = movieId)
                _state.value = state.value.copy(castList = movies)
            }
        }catch (e:Exception){
            Log.v("ERROR",e.message.toString())
        }


    }

    private fun getMovieKey(movieId: Int) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val movieKey = moviesUseCases.getMovieKey.invoke(movieId = movieId)
                movieKey?.let {
                    if (movieKey.results.isNotEmpty()) {
                        _state.value = state.value.copy(movieKey = movieKey.results[0].key)
                    }
                }


            }
        }catch (e: Exception){
            Log.v("ERROR",e.message.toString())
        }

    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpdateMovieId -> {
                getMovieCast(event.movieId)
                getMovieKey(event.movieId)
            }

            is DetailsEvent.UpdateMovieGenre -> {
                getMovieGenre(event.genres)
            }

            is DetailsEvent.SaveDeleteMovie -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val movie = moviesUseCases.getMovie(movieId = event.movie.id)
                    if (movie == null) { // I think this to check if it in the db
                        upsertMovie(event.movie)
                    } else {
                        deleteMovie(event.movie)
                    }
                }
            }

            is DetailsEvent.CheckSaveStatus -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val isSaved = moviesUseCases.getMovie(movieId = event.movieId) != null
                    withContext(Dispatchers.Main) {
                        _state.value = _state.value.copy(savedStatus = isSaved)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null

            }
        }
    }

    private fun deleteMovie(movie: Movies) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesUseCases.deleteMovie(movie)
            sideEffect = "Movie deleted :("
        }

    }

    private fun upsertMovie(movie: Movies) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesUseCases.upsertMovie(movie)
            sideEffect = "Movie Saved"

        }
    }

    private fun getMovieGenre(genreIds: List<Int>) {
        val genreMap = mapOf(
            28 to "Action",
            12 to "Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Crime",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            14 to "Fantasy",
            36 to "History",
            27 to "Horror",
            10402 to "Music",
            9648 to "Mystery",
            10749 to "Romance",
            878 to "Science Fiction",
            10770 to "TV Movie",
            53 to "Thriller",
            10752 to "War",
            37 to "Western"
        )

        val genres: List<String> = genreIds.mapNotNull { genreMap[it] }
        _state.value = _state.value.copy(genres = genres)

    }


}