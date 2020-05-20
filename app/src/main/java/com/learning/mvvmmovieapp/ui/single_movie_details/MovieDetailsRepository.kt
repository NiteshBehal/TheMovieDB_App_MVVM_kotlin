package com.learning.mvvmmovieapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.learning.mvvmmovieapp.data.api.TheMovieDBClient
import com.learning.mvvmmovieapp.data.api.TheMovieDBInterface
import com.learning.mvvmmovieapp.data.repository.MovieDetailsNetworkDataSource
import com.learning.mvvmmovieapp.data.repository.NetworkState
import com.learning.mvvmmovieapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

/**
 * if we want to store the data into local db, we can do it in this class
 */
class MovieDetailsRepository() {

    private val apiService: TheMovieDBInterface by lazy {
        TheMovieDBClient.getClient()
    }

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}