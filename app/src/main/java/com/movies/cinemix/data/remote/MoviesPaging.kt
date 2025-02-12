package com.movies.cinemix.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.model.Result

class MoviesPaging(
    private val moviesApi: MoviesApi
) : PagingSource<Int,Result>(){
    private var totalMoviesCount = 0
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPosition = anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1

        return try {
            val moviesResponse = moviesApi.getNowPlayingMovies(page = page)
            totalMoviesCount += moviesResponse!!.results.size
            val movies =
                moviesResponse.results
            LoadResult.Page(
                data = movies,
                nextKey = if ( totalMoviesCount == moviesResponse.total_results) null else page + 1,
                prevKey = null
            )
        }catch (e:Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

}