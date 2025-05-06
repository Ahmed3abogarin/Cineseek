package com.movies.cinemix.data.remote

import com.movies.cinemix.BuildConfig
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.MovieKeyResponse
import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse


    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse

    @GET("trending/movie/week")
    suspend fun getTrendingWeek(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse

    @GET("discover/movie")
    suspend fun getMarvelMovies(
        @Query("with_companies") num: Int = 420,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse

    @GET("search/movie")
    suspend fun getMovie(
        @Query("query") movieName: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCrew(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): CastResponse


    @GET("movie/{movie_id}/videos")
    suspend fun getMovieKey(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieKeyResponse

    @GET("person/{person_id}")
    suspend fun getPersonInfo(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): PersonResponse


    @GET("discover/movie")
    suspend fun getPersonMovies(
        @Query("with_cast") personId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse

    @GET("discover/movie")
    suspend fun getArabicMovies(
        @Query("with_original_language") arabic: String = "ar",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse


    @GET("discover/movie")
    suspend fun getGenreMovies(
        @Query("with_genres") genreNum: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieResponse

}