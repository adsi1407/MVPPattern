package com.lupesoft.mvppattern.movie.controler.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.databinding.FragmentAboutOfMovieLayoutBinding
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.movie.presenter.MoviePresenter
import com.lupesoft.mvppattern.movie.presenter.MovieView
import com.lupesoft.mvppattern.shared.controler.extensions.isHide
import com.lupesoft.mvppattern.shared.controler.extensions.loadImageDetail
import com.lupesoft.mvppattern.shared.controler.extensions.showMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutOfMovieFragment : Fragment(), MovieView {

    @Inject
    lateinit var moviePresenter: MoviePresenter

    private lateinit var binding: FragmentAboutOfMovieLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutOfMovieLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviePresenter.movieView = this
        subscribeClick()
    }

    private fun subscribeClick() {
        with(binding) {
            loaderAbout.root.isHide(false)
            setDataMovie()
            fabActionShoppingCart.setOnClickListener { showMenu(it) }
        }
    }

    private fun setDataMovie() {
        with(binding) {
            (arguments?.get("movie") as Movie).let {
                imageMovie.loadImageDetail(it.posterPath)
                itemName.text = it.title
                itemDescription.text = it.overview
                itemPopularity.text = it.popularity.toString()
            }
            loaderAbout.root.isHide(true)
        }
    }

    private fun showMenu(v: View) {
        PopupMenu(requireContext(), v).apply {
            menuInflater.inflate(R.menu.popup_menu_detail, menu)
            setOnMenuItemClickListener { menuItem: MenuItem ->
                (arguments?.get("movie") as Movie).let { data ->
                    when (menuItem.itemId) {
                        R.id.add_movie_detail -> {
                            moviePresenter.addMovie(data.id)
                            true
                        }
                        R.id.delete_movie_detail -> {
                            moviePresenter.deleteMovie(data.id)
                            true
                        }
                        else -> false
                    }
                } ?: false
            }
        }.also { it.show() }
    }

    override fun getLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun setDataMovie(data: List<Movie>?) {
    }

    override fun showProgress() {
        binding.loaderAbout.root.isHide(false)
    }

    override fun hideProgress() {
        binding.loaderAbout.root.isHide(true)
    }

    override fun showError(message: String?) {
        (message
            ?: requireContext().getString(R.string.something_unexpected_happened)
                ).showMessage(requireContext())
    }
}