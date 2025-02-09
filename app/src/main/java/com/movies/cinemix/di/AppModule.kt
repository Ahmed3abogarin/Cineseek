package com.movies.cinemix.di

import com.movies.cinemix.data.remote.MoviesApi
import com.movies.cinemix.data.repository.MoviesRepositoryImpl
import com.movies.cinemix.domain.repository.MoviesRepository
import com.movies.cinemix.util.Constants.NOW_PLAYING_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(): MoviesApi{
        return Retrofit.Builder()
            .baseUrl(NOW_PLAYING_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MoviesApi) :MoviesRepository{
        return MoviesRepositoryImpl(api)

    }
}