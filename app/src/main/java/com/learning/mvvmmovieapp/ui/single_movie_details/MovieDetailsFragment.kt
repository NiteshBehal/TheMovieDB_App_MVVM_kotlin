package com.learning.mvvmmovieapp.ui.single_movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.learning.mvvmmovieapp.R
import com.learning.mvvmmovieapp.data.api.POSTER_BASE_URL
import com.learning.mvvmmovieapp.data.repository.NetworkState
import com.learning.mvvmmovieapp.data.vo.MovieDetails
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.text.NumberFormat
import java.util.*

class MovieDetailsFragment : Fragment() {

    private lateinit var detailsViewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var movieId = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movieId
        detailsViewModel = getViewModel(movieId)
        detailsViewModel.movieDetails.observe(requireActivity(), Observer {
            bindUI(it)
        })
        detailsViewModel.networkState.observe(requireActivity(), Observer {
            progress_bar.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUI(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formateCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formateCurrency.format(it.budget)
        movie_revenue.text = formateCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this).load(moviePosterURL).into(iv_movie_poster)

    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModel(movieId: Int): MovieDetailsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieDetailsViewModel(movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }

}