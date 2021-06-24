package com.lupesoft.mvppattern.cart.controler.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.cart.presenter.CartPresenter
import com.lupesoft.mvppattern.cart.presenter.CartView
import com.lupesoft.mvppattern.databinding.FragmentShoppingCartLayoutBinding
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.presenter.adapters.MovieAdapter
import com.lupesoft.mvppattern.presenter.adapters.setDataMovie
import com.lupesoft.mvppattern.shared.controler.extensions.isHide
import com.lupesoft.mvppattern.shared.controler.extensions.showMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingCartFragment : Fragment(), CartView {

    @Inject
    lateinit var cartPresenter: CartPresenter

    private lateinit var binding: FragmentShoppingCartLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCartLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartPresenter.cartView = this
        subscribeActionShoppingCart()
        cartPresenter.updateListMovies()
        subscribeActionRemoveAllMovies()
    }


    private fun subscribeActionRemoveAllMovies() {
        with(binding) {
            fabRemoveAllMovies.setOnClickListener {
                loaderShopping.root.isHide(true)
                cartPresenter.deleteAllMovie()
            }
        }
    }

    private fun subscribeActionShoppingCart() {
        with(binding) {
            shoppingCartMovieList.adapter = MovieAdapter(
                { _, movieId ->
                    cartPresenter.deleteMovie(movieId)
                }, menuRes = R.menu.popup_menu_shopping
            )
        }
    }

    override fun getLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun setDataMovie(data: List<Movie>?) {
        binding.shoppingCartMovieList.setDataMovie(data, binding.listEmptyCart)
    }

    override fun showProgress() {
        binding.loaderShopping.root.isHide(false)
    }

    override fun hideProgress() {
        binding.loaderShopping.root.isHide(true)
    }

    override fun showError(message: String?) {
        (message
            ?: requireContext().getString(R.string.something_unexpected_happened)
                ).showMessage(requireContext())
    }
}