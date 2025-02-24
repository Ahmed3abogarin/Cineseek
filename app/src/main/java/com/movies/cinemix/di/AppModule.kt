package com.movies.cinemix.di

import android.app.Application
import androidx.room.Room
import com.movies.cinemix.data.local.MovieDatabase
import com.movies.cinemix.data.local.MoviesDao
import com.movies.cinemix.data.local.MoviesTypeConverter
import com.movies.cinemix.data.remote.MoviesApi
import com.movies.cinemix.data.repository.MoviesRepositoryImpl
import com.movies.cinemix.domain.repository.MoviesRepository
import com.movies.cinemix.domain.usecases.DeleteMovie
import com.movies.cinemix.domain.usecases.GetArabicMovies
import com.movies.cinemix.domain.usecases.GetMovie
import com.movies.cinemix.domain.usecases.GetMovieCast
import com.movies.cinemix.domain.usecases.GetMovieKey
import com.movies.cinemix.domain.usecases.GetMovies
import com.movies.cinemix.domain.usecases.GetNowPlayingMovies
import com.movies.cinemix.domain.usecases.GetPersonInfo
import com.movies.cinemix.domain.usecases.GetPersonMovies
import com.movies.cinemix.domain.usecases.GetPopularMovies
import com.movies.cinemix.domain.usecases.GetTopRatedMovies
import com.movies.cinemix.domain.usecases.GetTrendWeek
import com.movies.cinemix.domain.usecases.GetUpcomingMovies
import com.movies.cinemix.domain.usecases.MoviesUseCases
import com.movies.cinemix.domain.usecases.SearchMovie
import com.movies.cinemix.domain.usecases.UpsertMovie
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
    fun provideMoviesRepository(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(NOW_PLAYING_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesUseCases(
        moviesRepository: MoviesRepository,
    ): MoviesUseCases = MoviesUseCases(
        getNowPlayingMovies = GetNowPlayingMovies(moviesRepository),
        getPopularMovies = GetPopularMovies(moviesRepository),
        getTopRatedMovies = GetTopRatedMovies(moviesRepository),
        getUpcomingMovies = GetUpcomingMovies(moviesRepository),
        getArabicMovies = GetArabicMovies(moviesRepository),
        getMovieCast = GetMovieCast(moviesRepository),
        getMovieKey = GetMovieKey(moviesRepository),
        getTrendWeek = GetTrendWeek(moviesRepository),
        searchMovie = SearchMovie(moviesRepository),
        upsertMovie = UpsertMovie(moviesRepository),
        deleteMovie = DeleteMovie(moviesRepository),
        getMovies = GetMovies(moviesRepository),
        getMovie = GetMovie(moviesRepository),
        getPersonInfo = GetPersonInfo(moviesRepository),
        getPersonMovies = GetPersonMovies(moviesRepository)
    )

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MoviesApi,
        moviesDao: MoviesDao
    ): MoviesRepository = MoviesRepositoryImpl(api, moviesDao)


    @Provides
    @Singleton
    fun provideMoviesDatabase(
        application: Application,
    ): MovieDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = MovieDatabase::class.java,
            name = "movies_db" // gotta move to constant
        ).addTypeConverter(MoviesTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(
        moviesDatabase: MovieDatabase,
    ): MoviesDao = moviesDatabase.moviesDao
}