package com.app.upcomingmovies.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.upcomingmovies.R
import com.app.upcomingmovies.adapter.MoviesAdapter
import com.app.upcomingmovies.response.Movie
import com.app.upcomingmovies.ui.base.BaseFragment
import com.app.upcomingmovies.utils.toast
import com.app.upcomingmovies.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private val mMovieList = mutableListOf<Movie>()

    private val mMoviesAdapter = MoviesAdapter(mMovieList, ::onMovieClicked)

    private val viewModel: MovieListViewModel by viewModel()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = mMoviesAdapter
        }
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            with(mMovieList) {
                clear()
                addAll(it)
                mMoviesAdapter.notifyDataSetChanged()
            }
            setTitle(getString(R.string.app_name))
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer {
            requireContext().toast(it)
        })
    }

    override fun setTitle(title: String) {
        tvTitle?.text = getString(R.string.app_name)
    }

    override fun getLayout(): Int = R.layout.home_fragment

    private fun onMovieClicked(movieId: Long, title: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieId, title)
        findNavController().navigate(action, options)
    }
}