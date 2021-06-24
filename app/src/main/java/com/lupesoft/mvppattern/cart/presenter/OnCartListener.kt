package com.lupesoft.mvppattern.cart.presenter

import androidx.lifecycle.LiveData
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.response.Resource

interface OnCartListener {
    fun onGetAllMoviesIntoShoppingCart(allMovies: LiveData<Resource<List<Movie>>>)
    fun onDeleteMovie(success: LiveData<Resource<Int>>)
}