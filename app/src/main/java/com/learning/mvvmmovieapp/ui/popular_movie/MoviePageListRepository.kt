package com.learning.mvvmmovieapp.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.learning.mvvmmovieapp.data.api.POST_PER_PAGE
import com.learning.mvvmmovieapp.data.api.TheMovieDBInterface
import com.learning.mvvmmovieapp.data.repository.MovieDataSource
import com.learning.mvvmmovieapp.data.repository.MovieDataSourceFactory
import com.learning.mvvmmovieapp.data.repository.NetworkState
import com.learning.mvvmmovieapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()
        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }
}