package com.learning.mvvmmovieapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "9bf0ca8a874ab7d305f9520102b3e27b"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

    const val FIRST_PAGE = 1
    const val POST_PER_PAGE = 20

/**
 * We have created it as an Object(Static) so that we don't need to instantiate it when we use it.
 */
object TheMovieDBClient {

    fun getClient(): TheMovieDBInterface {
        val requestInterceptor = Interceptor { chain ->
            // Interceptor takes only one argument which is a lambda function so parenthesis can be omitted.
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
//            explicitly return a value from with @ annotation. lambda always returns the value of the last expression implicitly
        }

//        adding the above interceptor to OK HTTP client
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)
    }

}