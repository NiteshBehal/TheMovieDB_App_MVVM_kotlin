package com.learning.mvvmmovieapp.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {
    //    We create companion object when we want to make something static
    companion object {
        @JvmStatic
        val LOADED: NetworkState
        @JvmStatic
        val LOADING: NetworkState
        @JvmStatic
        val ERROR: NetworkState
        @JvmStatic
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.FAILED, "Something went wrong")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end")
        }
    }
}