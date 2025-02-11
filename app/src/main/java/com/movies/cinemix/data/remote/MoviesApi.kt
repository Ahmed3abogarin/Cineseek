package com.movies.cinemix.data.remote

import com.movies.cinemix.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "9b574fdbc36ea62e7f01114df3589156"
    ): Movie?
}