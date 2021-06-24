package com.lupesoft.mvppattern.cart.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.Status
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.response.Resource
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class CartPresenter @Inject constructor(
    private val cartInteractor: CartInteractor
) : OnCartListener {

    var cartView: CartView? = null
        set(value) {
            field = value
            cartInteractor.onCartListener = this 
        }

    fun deleteAllMovie() {
        cartInteractor.deleteAllMovie()
    }

    fun deleteMovie(movieId: Int) {
        cartInteractor.deleteMovie(movieId)
    }

    private fun <T> actionEventAddOrRemove(result: Resource<T>) {
        when (result.status) {
            Status.LOADING -> cartView?.showProgress()
            Status.SUCCESS -> {
                updateListMovies()
            }
            Status.ERROR -> cartView?.hideProgress()
        }
        if (result.status != Status.LOADING) {
            cartView?.showError(result.message)
        }
    }

    fun updateListMovies() {
        cartView?.showProgress()
        cartInteractor.getAllMoviesIntoShoppingCart()
    }

    override fun onGetAllMoviesIntoShoppingCart(allMovies: LiveData<Resource<List<Movie>>>) {
        allMovies.observe(cartView!!.getLifecycleOwner(), Observer { result ->
            when (result.status) {
                Status.LOADING -> cartView?.showProgress()
                Status.SUCCESS -> {
                    cartView?.setDataMovie(result.data)
                    cartView?.hideProgress()
                }
                Status.ERROR -> {
                    cartView?.setDataMovie(null)
                    cartView?.hideProgress()
                    cartView?.showError(result.message)
                }
            }
        })
    }

    override fun onDeleteMovie(success: LiveData<Resource<Int>>) {
        success.observe(cartView!!.getLifecycleOwner(), Observer { result ->
            actionEventAddOrRemove(result)
        })
    }
}