package com.movies.cinemix.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.cinemix.domain.usecases.MoviesUseCases
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
                    val personInfo = moviesUseCases.getPersonInfo(event.personId)
                    _state.value = _state.value.copy(personInfo = personInfo)
                }
            }
        }
    }

}