package com.learning.mvvmmovieapp.ui.single_movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learning.mvvmmovieapp.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MovieDetailsFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var movieId = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movieId
        getViewModel(movieId).also {
            binding.movieDetailViewModel = it
        }

    }

    private fun getViewModel(movieId: Int): MovieDetailsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                    // Or better use here if it doesn't provides error @SuppressWarnings("unchecked")
                    return MovieDetailsViewModel(movieId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        })[MovieDetailsViewModel::class.java]
    }

}