package com.learning.mvvmmovieapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.learning.mvvmmovieapp.data.repository.NetworkState
import com.learning.mvvmmovieapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(movieId: Int) :
    ViewModel() {

    val movieRepository: MovieDetailsRepository by lazy {
        MovieDetailsRepository()
    }
    private val compositeDisposable = CompositeDisposable()
    val movieDetails: LiveData<MovieDetails> by lazy {
//        movieRepository will get initialized when required, so to improve performance we are using by lazy{}
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}