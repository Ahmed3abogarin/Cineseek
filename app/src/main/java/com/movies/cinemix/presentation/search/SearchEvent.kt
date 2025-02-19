package com.movies.cinemix.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String): SearchEvent()
    data object SearchMovie: SearchEvent()
}