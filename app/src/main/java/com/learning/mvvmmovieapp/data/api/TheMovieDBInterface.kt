package com.learning.mvvmmovieapp.data.api

import com.learning.mvvmmovieapp.data.vo.MovieDetails
import com.learning.mvvmmovieapp.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=YOUR_API_KEY&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=YOUR_API_KEY
    // https://api.themoviedb.org/3/


    /**
     * We can put api_key here but is not a good practice. We will add api key using interceptor
     *
     * @return Single<MovieDetails> Single is a type of Observable in ReactiveX or RX Java.
     * It will either emitts a single value or an error
     */
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

}