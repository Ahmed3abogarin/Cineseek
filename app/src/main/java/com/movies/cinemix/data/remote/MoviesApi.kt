package com.movies.cinemix.data.remote

import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page:Int,
        @Query("api_key") apiKey: String = "9b574fdbc36ea62e7f01114df3589156"
    ): MovieResponse


    @GET("popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "9b574fdbc36ea62e7f01114df3589156"
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "9b574fdbc36ea62e7f01114df3589156"
    ): MovieResponse


    @GET("upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "9b574fdbc36ea62e7f01114df3589156"
    ): MovieResponse


    @GET("{movie_id}/credits")
    suspend fun getMovieCrew(
        @Path("movie_id") movieId: Int ,
        @Query("api_key") apiKey: String = "9b574fdbc36ea62e7f01114df3589156"
    ): CastResponse
}