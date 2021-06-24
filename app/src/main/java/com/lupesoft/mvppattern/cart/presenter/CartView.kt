package com.lupesoft.mvppattern.cart.presenter

import androidx.lifecycle.LifecycleOwner
import com.lupesoft.mvppattern.movie.model.vo.Movie

interface CartView {
    fun getLifecycleOwner(): LifecycleOwner
    fun setDataMovie(data: List<Movie>?)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String?)
}