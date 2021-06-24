package com.lupesoft.mvppattern.movie.controler.dimodule

import com.lupesoft.mvppattern.movie.model.repositories.MovieRepository
import com.lupesoft.mvppattern.movie.model.dataAccess.repositories.MovieRepositoryRoom
import com.lupesoft.mvppattern.cart.model.dataAccess.repositories.ShoppingCartRepositoryRoom
import com.lupesoft.mvppattern.movie.model.repositories.ShoppingCartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@InstallIn(FragmentComponent::class)
@Module
abstract class MoviesModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryRoom: MovieRepositoryRoom): MovieRepository

    @Binds
    abstract fun provideShoppingCartRepository(shoppingCartRepositoryRoom: ShoppingCartRepositoryRoom): ShoppingCartRepository

}