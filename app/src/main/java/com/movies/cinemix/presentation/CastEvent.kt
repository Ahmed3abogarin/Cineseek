package com.movies.cinemix.presentation

sealed class CastEvent {
    data class UpdatePersonId(val personId: Int) : CastEvent()
}