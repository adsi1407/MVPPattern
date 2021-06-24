package com.lupesoft.mvppattern.movie.controler.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.databinding.FragmentAllMoviesLayoutBinding
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.movie.presenter.MoviePresenter
import com.lupesoft.mvppattern.movie.presenter.MovieView
import com.lupesoft.mvppattern.presenter.adapters.MovieAdapter
import com.lupesoft.mvppattern.presenter.adapters.setDataMovie
import com.lupesoft.mvppattern.shared.controler.extensions.isHide
import com.lupesoft.mvppattern.shared.controler.extensions.showMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllMoviesFragment : Fragment(), MovieView {

    @Inject
    lateinit var moviePresenter: MoviePresenter

    private lateinit var binding: FragmentAllMoviesLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllMoviesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviePresenter.movieView = this
        subscribeActionShoppingCart()
        subscribeUi()
    }

    private fun subscribeActionShoppingCart() {
        binding.movieList.adapter = MovieAdapter({ addOrDelete, movieId ->
            if (addOrDelete) {
                moviePresenter.addMovie(movieId)
            } else {
                moviePresenter.deleteMovie(movieId)
            }
        }, R.menu.popup_menu)
    }

    private fun subscribeUi() {
        with(binding) {
            loader.root.isHide(false)
            moviePresenter.getAllMovies()
        }
    }

    override fun getLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun setDataMovie(data: List<Movie>?) {
        binding.movieList.setDataMovie(data, binding.listEmpty)
    }

    override fun showProgress() {
        binding.loader.root.isHide(false)
    }

    override fun hideProgress() {
        binding.loader.root.isHide(true)
    }

    override fun showError(message: String?) {
        (message
            ?: requireContext().getString(R.string.something_unexpected_happened)
                ).showMessage(requireContext())
    }
}