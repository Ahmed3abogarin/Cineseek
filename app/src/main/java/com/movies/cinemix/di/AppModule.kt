package com.movies.cinemix.di

import android.app.Application
import androidx.room.Room
import com.movies.cinemix.data.local.MovieDatabase
import com.movies.cinemix.data.local.MoviesDao
import com.movies.cinemix.data.local.MoviesTypeConverter
import com.movies.cinemix.data.manager.LocalUserImpl
import com.movies.cinemix.data.remote.MoviesApi
import com.movies.cinemix.data.repository.MoviesRepositoryImpl
import com.movies.cinemix.domain.manager.LocalUserManager
import com.movies.cinemix.domain.repository.MoviesRepository
import com.movies.cinemix.domain.usecases.movies.DeleteMovie
import com.movies.cinemix.domain.usecases.movies.GetArabicMovies
import com.movies.cinemix.domain.usecases.movies.GetGenreMovies
import com.movies.cinemix.domain.usecases.movies.GetMarvelMovies
import com.movies.cinemix.domain.usecases.movies.GetMovie
import com.movies.cinemix.domain.usecases.movies.GetMovieCast
import com.movies.cinemix.domain.usecases.movies.GetMovieKey
import com.movies.cinemix.domain.usecases.movies.GetMovies
import com.movies.cinemix.domain.usecases.movies.GetNowPlayingMovies
import com.movies.cinemix.domain.usecases.movies.GetPersonInfo
import com.movies.cinemix.domain.usecases.movies.GetPersonMovies
import com.movies.cinemix.domain.usecases.movies.GetPopularMovies
import com.movies.cinemix.domain.usecases.movies.GetTopRatedMovies
import com.movies.cinemix.domain.usecases.movies.GetTrendWeek
import com.movies.cinemix.domain.usecases.movies.GetUpcomingMovies
import com.movies.cinemix.domain.usecases.movies.MoviesUseCases
import com.movies.cinemix.domain.usecases.movies.SearchMovie
import com.movies.cinemix.domain.usecases.movies.UpsertMovie
import com.movies.cinemix.domain.usecases.app_entry.AppEntryUseCases
import com.movies.cinemix.domain.usecases.app_entry.ReadAppEntry
import com.movies.cinemix.domain.usecases.app_entry.SaveAppEntry
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
        getGenreMovies = GetGenreMovies(moviesRepository),
        searchMovie = SearchMovie(moviesRepository),
        upsertMovie = UpsertMovie(moviesRepository),
        deleteMovie = DeleteMovie(moviesRepository),
        getMovies = GetMovies(moviesRepository),
        getMovie = GetMovie(moviesRepository),
        getPersonInfo = GetPersonInfo(moviesRepository),
        getPersonMovies = GetPersonMovies(moviesRepository),
        getMarvelMovies = GetMarvelMovies(moviesRepository)
    )

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MoviesApi,
        moviesDao: MoviesDao,
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


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager,
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserImpl(application)
}