package com.lupesoft.mvppattern.movie.presenter

import androidx.lifecycle.LifecycleOwner
import com.lupesoft.mvppattern.movie.model.vo.Movie

interface MovieView {
    fun getLifecycleOwner(): LifecycleOwner
    fun setDataMovie(data: List<Movie>?)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String?)
}