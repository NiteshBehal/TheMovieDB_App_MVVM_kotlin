package com.learning.mvvmmovieapp.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.learning.mvvmmovieapp.data.repository.NetworkState
import com.learning.mvvmmovieapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(): ViewModel() {
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }
    private val movieRepository: MoviePageListRepository by lazy {
        MoviePageListRepository()
    }

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState>by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{
        return  moviePagedList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}