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



    private fun getMovieCast(){
        viewModelScope.launch {
            val movies = moviesUseCases.getMovieCast.invoke(movieId = state.value.movieId!!)
            _state.value = state.value.copy(castList = movies)
        }

    }

    private fun getMovieKey(){
        viewModelScope.launch {
            val movieKey = moviesUseCases.getMovieKey.invoke(movieId = state.value.movieId!!)
            _state.value = state.value.copy(movieKey = movieKey.results[0].key)
        }
    }

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.UpdateMovieId ->{
                _state.value = state.value.copy(movieId = event.movieId)
                getMovieCast()
                getMovieKey()


            }
        }
    }



}