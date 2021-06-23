package com.lupesoft.mvppattern.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.databinding.FragmentAllMoviesLayoutBinding
import com.lupesoft.mvppattern.model.dataAccess.utils.Status
import com.lupesoft.mvppattern.presenter.extensions.showMessage
import com.lupesoft.mvppattern.model.dataAccess.utils.response.Resource
import com.lupesoft.mvppattern.model.models.MovieModel
import com.lupesoft.mvppattern.model.models.ShoppingCartModel
import com.lupesoft.mvppattern.presenter.adapters.MovieAdapter
import com.lupesoft.mvppattern.presenter.adapters.setDataMovie
import com.lupesoft.mvppattern.presenter.extensions.isHide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllMoviesFragment : Fragment() {

    private lateinit var binding: FragmentAllMoviesLayoutBinding

    @Inject
    lateinit var movieModel: MovieModel

    @Inject
    lateinit var shoppingCartModel: ShoppingCartModel

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
        subscribeActionShoppingCart()
        subscribeUi()
    }

    private fun <T> actionEventAddOrRemove(result: Resource<T>) {
        with(binding) {
            when (result.status) {
                Status.LOADING -> loader.root.isHide(false)
                Status.SUCCESS -> loader.root.isHide(true)
                Status.ERROR -> loader.root.isHide(true)
            }
            if (result.status != Status.LOADING) {
                (result.message
                    ?: requireContext().getString(R.string.something_unexpected_happened))
                    .showMessage(requireContext())
            }
        }
    }

    private fun subscribeActionShoppingCart() {
        with(binding) {
            binding.movieList.adapter = MovieAdapter({ addOrDelete, movieId ->
                loader.root.isHide(true)
                if (addOrDelete) {
                    shoppingCartModel.addMovie(movieId)
                        .observe(viewLifecycleOwner, Observer { result ->
                            actionEventAddOrRemove(result)
                        })
                } else {
                    shoppingCartModel.deleteMovie(movieId)
                        .observe(viewLifecycleOwner, Observer { result ->
                            actionEventAddOrRemove(result)
                        })
                }
            }, R.menu.popup_menu)
        }
    }

    private fun subscribeUi() {
        with(binding) {
            loader.root.isHide(false)
            movieModel.getAllMovies().observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> loader.root.isHide(false)
                    Status.SUCCESS -> {
                        movieList.setDataMovie(result.data, listEmpty)
                        loader.root.isHide(true)
                    }
                    Status.ERROR -> {
                        movieList.setDataMovie(null, listEmpty)
                        loader.root.isHide(true)
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            })
        }
    }
}