package com.movies.cinemix.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.usecases.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state





    fun openDialog(){
        _state.value = _state.value.copy(showDialog = true)
    }
    fun closeDialog(){
        _state.value = _state.value.copy(showDialog = false)
    }



    private fun getMovieCast(movieId: Int){
        viewModelScope.launch {
            val movies = moviesUseCases.getMovieCast.invoke(movieId = movieId)
            _state.value = state.value.copy(castList = movies)
        }

    }

    private fun getMovieKey(movieId: Int){
        viewModelScope.launch {
            val movieKey = moviesUseCases.getMovieKey.invoke(movieId = movieId)
            if (movieKey.results.isNotEmpty()){
                _state.value = state.value.copy(movieKey = movieKey.results[0].key)
            }

        }
    }

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.UpdateMovieId ->{
                getMovieCast(event.movieId)
                getMovieKey(event.movieId)
            }
            is DetailsEvent.UpdateMovieGenre ->{
                getMovieGenre(event.genres)
            }
        }
    }

    private fun getMovieGenre(genreIds: List<Int>){
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