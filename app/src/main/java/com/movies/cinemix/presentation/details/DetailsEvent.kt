package com.movies.cinemix.presentation.details

import com.movies.cinemix.domain.model.MovieDetails

sealed class DetailsEvent {

    data class SaveDeleteMovie(val movie: MovieDetails) : DetailsEvent()
    data object RemoveSideEffect : DetailsEvent()

    data class AddLastMovie(val movieId: Int): DetailsEvent()

    data class CheckSaveStatus(val movieId: Int) : DetailsEvent()
    data object CheckTrailerStatus : DetailsEvent()


}