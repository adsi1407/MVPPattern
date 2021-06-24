package com.lupesoft.mvppattern.movie.model.repositories

import com.lupesoft.mvppattern.movie.model.vo.Movie

interface ShoppingCartRepository {

    fun getAllMoviesIntoShoppingCart(): List<Movie>

    fun addMovie(idMovie: Int): Long

    fun deleteMovie(idMovie: Int): Int

    fun deleteAllMovie(): Int
}