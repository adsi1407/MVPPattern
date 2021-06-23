package com.lupesoft.mvppattern.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.databinding.FragmentAboutOfMovieLayoutBinding
import com.lupesoft.mvppattern.model.dataAccess.utils.Status
import com.lupesoft.mvppattern.presenter.extensions.showMessage
import com.lupesoft.mvppattern.model.dataAccess.utils.response.Resource
import com.lupesoft.mvppattern.model.models.ShoppingCartModel
import com.lupesoft.mvppattern.model.vo.Movie
import com.lupesoft.mvppattern.presenter.extensions.isHide
import com.lupesoft.mvppattern.presenter.extensions.loadImageDetail
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutOfMovieFragment : Fragment() {


    @Inject
    lateinit var shoppingCartModel: ShoppingCartModel

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
        subscribeClick()
    }

    private fun subscribeClick() {
        with(binding) {
            loaderAbout.root.isHide(false)
            shoppingCartModel.movie = (arguments?.get("movie") as Movie)
            setDataMovie()
            fabActionShoppingCart.setOnClickListener { showMenu(it) }
        }
    }

    private fun setDataMovie() {
        with(binding) {
            shoppingCartModel.movie?.let {
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
                shoppingCartModel.movie?.let { data ->
                    when (menuItem.itemId) {
                        R.id.add_movie_detail -> {
                            shoppingCartModel.addMovie(data.id)
                                .observe(viewLifecycleOwner, Observer { result ->
                                    actionEventAddOrRemove(result)
                                })
                            true
                        }
                        R.id.delete_movie_detail -> {
                            shoppingCartModel.deleteMovie(data.id)
                                .observe(viewLifecycleOwner, Observer { result ->
                                    actionEventAddOrRemove(result)
                                })
                            true
                        }
                        else -> false
                    }
                } ?: false
            }
        }.also { it.show() }
    }

    private fun <T> actionEventAddOrRemove(result: Resource<T>) {
        with(binding) {
            when (result.status) {
                Status.LOADING -> loaderAbout.root.isHide(false)
                Status.SUCCESS -> loaderAbout.root.isHide(true)
                Status.ERROR -> loaderAbout.root.isHide(true)
            }
            if (result.status != Status.LOADING) {
                (result.message
                    ?: requireContext().getString(R.string.something_unexpected_happened)
                        ).showMessage(requireContext())
            }
        }
    }
}