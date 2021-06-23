package com.lupesoft.mvppattern.model.dataAccess.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lupesoft.mvppattern.model.dataAccess.entities.MovieEntity
import com.lupesoft.mvppattern.model.dataAccess.entities.ShoppingCarEntity

@Dao
interface ShoppingCartDao {

    @Insert
    fun addMovie(shoppingCartEntity: ShoppingCarEntity): Long

    @Query("DELETE FROM ShoppingCart WHERE idMovieIntoShopCart = :id")
    fun deleteMovie(id: Int): Int

    @Query("DELETE FROM ShoppingCart")
    fun deleteAllMoviesIntoShoppingCart(): Int

    @Query("SELECT Movie.* FROM ShoppingCart LEFT JOIN Movie ON Movie.idMovie = ShoppingCart.idMovieIntoShopCart")
    fun getAllMoviesIntoShoppingCart(): List<MovieEntity>
}