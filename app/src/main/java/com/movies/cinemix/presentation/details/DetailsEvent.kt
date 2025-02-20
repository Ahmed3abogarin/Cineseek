package com.movies.cinemix.presentation.details

import com.movies.cinemix.domain.model.Movies

sealed class DetailsEvent {
    data class UpdateMovieId(val movieId: Int): DetailsEvent()
    data class UpdateMovieGenre(val genres: List<Int>): DetailsEvent()

    data class SaveDeleteMovie(val movie: Movies): DetailsEvent()
    data object RemoveSideEffect: DetailsEvent()

    data class CheckSaveStatus(val movieId: Int): DetailsEvent()


}