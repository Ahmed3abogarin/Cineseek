package com.movies.cinemix.presentation.castdetails

sealed class CastEvent {
    data class UpdatePersonId(val personId: Int) : CastEvent()
}