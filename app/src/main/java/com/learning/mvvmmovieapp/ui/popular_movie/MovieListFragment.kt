package com.learning.mvvmmovieapp.ui.popular_movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.learning.mvvmmovieapp.databinding.FragmentMovieListBinding
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {

    private lateinit var movieAdapter: MoviePagedListAdapter
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MovieListFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java).also {
            binding.movieListViewModel = it
        }
        setupMovieListAdapter()
        setViewModleObservers()
    }

    private fun setupMovieListAdapter() {

        movieAdapter = MoviePagedListAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };

        rv_movie_list.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setViewModleObservers() {
        viewModel.apply {
            moviePagedList.observe(requireActivity(), Observer {
                movieAdapter.submitList(it)
            })
            networkState.observe(requireActivity(), Observer {
                if (!listIsEmpty()) {
                    movieAdapter.setNetworkState(it)
                }
            })
        }
    }
}