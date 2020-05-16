package com.learning.mvvmmovieapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.learning.mvvmmovieapp.data.api.TheMovieDBInterface
import com.learning.mvvmmovieapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}