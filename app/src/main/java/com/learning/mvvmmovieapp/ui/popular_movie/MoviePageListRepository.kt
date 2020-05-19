package com.learning.mvvmmovieapp.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.learning.mvvmmovieapp.data.api.POST_PER_PAGE
import com.learning.mvvmmovieapp.data.api.TheMovieDBClient
import com.learning.mvvmmovieapp.data.api.TheMovieDBInterface
import com.learning.mvvmmovieapp.data.repository.MovieListDataSource
import com.learning.mvvmmovieapp.data.repository.MovieListDataSourceFactory
import com.learning.mvvmmovieapp.data.repository.NetworkState
import com.learning.mvvmmovieapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository() {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieListDataSourceFactory: MovieListDataSourceFactory

    private val apiService: TheMovieDBInterface by lazy {
        TheMovieDBClient.getClient()
    }

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieListDataSourceFactory = MovieListDataSourceFactory(apiService, compositeDisposable)
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()
        moviePagedList = LivePagedListBuilder(movieListDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieListDataSource, NetworkState>(
            movieListDataSourceFactory.moviesLiveDataSource, MovieListDataSource::networkState
        )
    }
}