package com.movies.cinemix.presentation.details

sealed class DetailsEvent {
    data class UpdateMovieId(val movieId: Int): DetailsEvent()
    data class UpdateMovieGenre(val genres: List<Int>): DetailsEvent()


}