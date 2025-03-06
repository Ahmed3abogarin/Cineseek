package com.movies.cinemix.presentation.castdetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
) : ViewModel() {

    private var _state = mutableStateOf(CastState())
    var state: State<CastState> = _state


    fun onEvent(event: CastEvent) {
        viewModelScope.launch {
            when (event) {
                is CastEvent.UpdatePersonId -> {
                    getPersonMovies(event.personId)
                    Log.v("PERSONID",event.personId.toString())
                    getPersonInfo(event.personId)

                }
            }
        }
    }

    private fun getPersonMovies(personId: Int) {

            val personMovies = moviesUseCases.getPersonMovies(personId)
            _state.value = _state.value.copy(personMovies = personMovies)

    }

    private fun getPersonInfo(personId: Int) {
        viewModelScope.launch {
            val personInfo = moviesUseCases.getPersonInfo.invoke(personId)
            _state.value = _state.value.copy(personInfo = personInfo)
        }

    }

}