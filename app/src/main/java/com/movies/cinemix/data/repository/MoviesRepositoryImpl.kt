package com.movies.cinemix.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.movies.cinemix.SingleMovie
import com.movies.cinemix.data.local.LastMoviesDao
import com.movies.cinemix.data.local.MoviesDao
import com.movies.cinemix.data.remote.MoviesApi
import com.movies.cinemix.data.remote.MoviesPaging
import com.movies.cinemix.domain.model.CastResponse
import com.movies.cinemix.domain.model.LastMovies
import com.movies.cinemix.domain.model.MovieKeyResponse
import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.domain.model.PersonResponse
import com.movies.cinemix.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val lastMoviesDao: LastMoviesDao,
) : MoviesRepository {
    override fun getNowPlayingMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getNowPlayingMovies(page) }
            }
        ).flow
    }

    override fun getPopularMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getPopularMovies(page) }
            }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getTopRatedMovies(page) }
            }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getUpcomingMovies(page) }
            }
        ).flow
    }

    override fun getTrendingWeek(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getTrendingWeek(page) }
            }
        ).flow
    }

    override fun getGenreMovies(genreNum: Int): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getGenreMovies(genreNum = genreNum, page = page) }
            }
        ).flow
    }

    override fun getMarvelsMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getMarvelMovies(page = page) }
            }
        ).flow
    }

    override fun searchMovie(movieName: String): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getMovie(page = page, movieName = movieName) }
            }
        ).flow
    }


    override suspend fun getMovieCrew(movieId: Int): CastResponse? {
        return try {
            moviesApi.getMovieCrew(movieId = movieId)
        } catch (e: Exception) {
            Log.v("Movies", e.message.toString())
            null
        }
    }

    override suspend fun getMovieKey(movieId: Int): MovieKeyResponse? {
        return try {
            moviesApi.getMovieKey(movieId = movieId)
        } catch (e: Exception) {
            Log.v("Movies", e.message.toString())
            null
        }
    }

    override suspend fun upsertMovie(movie: SingleMovie) {
        moviesDao.upsert(movie)
    }

    override suspend fun deleteMovie(movie: SingleMovie) {
        moviesDao.delete(movie)
    }

    override fun getMovies(): Flow<List<SingleMovie>> {
        return moviesDao.getMovies()
    }

    override fun getMovie(movieId: Int): SingleMovie? {
        return moviesDao.getMovie(movieId)
    }

    override suspend fun getPersonInfo(personId: Int): PersonResponse {
        return moviesApi.getPersonInfo(personId)
    }

    override fun getPersonMovies(personId: Int): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getPersonMovies(personId = personId, page = page) }
            }
        ).flow
    }

    override fun getArabicMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                MoviesPaging { page -> moviesApi.getArabicMovies(page = page) }
            }
        ).flow
    }

    override suspend fun upsertLastMovie(movieId: Int) {
        lastMoviesDao.upsertMovie(LastMovies(movieId))
    }

    override suspend fun getLastMovies(): List<SingleMovie> {
        Log.v("TTOO", "from impl called")
        val movies = mutableListOf<SingleMovie>()

        val listOfMovieIds = lastMoviesDao.getLastMovies().first()  // collect only the first emission
        listOfMovieIds.forEach { movieId ->
            getMovieById(movieId)?.let { movie ->
                movies.add(movie)
            }
        }

        Log.v("TTOO", "List size is: ${movies.size}")
        return movies
    }


    override suspend fun getMovieById(movieId: Int): SingleMovie {
        return moviesApi.getMovieById(movieId)
    }

    override suspend fun getRandomMovie(page: Int): MovieResponse? {
        return try {
            return moviesApi.getRandomMovie(page = page)
        } catch (e: Exception) {
            Log.v("TTOO", "The error: ${e.message}")
            null
        }
    }
}