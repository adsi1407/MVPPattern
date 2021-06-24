package com.lupesoft.mvppattern.cart.controler.dimodule

import com.lupesoft.mvppattern.cart.presenter.CartInteractor
import com.lupesoft.mvppattern.cart.presenter.CartPresenter
import com.lupesoft.mvppattern.cart.presenter.CartView
import com.lupesoft.mvppattern.cart.presenter.OnCartListener
import com.lupesoft.mvppattern.movie.model.networking.daos.MovieDaoRetrofit
import com.lupesoft.mvppattern.movie.model.repositories.MovieRepository
import com.lupesoft.mvppattern.movie.model.repositories.ShoppingCartRepository
import com.lupesoft.mvppattern.movie.presenter.MovieInteractor
import com.lupesoft.mvppattern.movie.presenter.MoviePresenter
import com.lupesoft.mvppattern.movie.presenter.MovieView
import com.lupesoft.mvppattern.movie.presenter.OnMovieListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(FragmentComponent::class)
@Module
class CartModule {

    @Provides
    fun provideMovieModel(
        movieRepository: MovieRepository,
        movieDaoRetrofit: MovieDaoRetrofit,
        shoppingCartRepository: ShoppingCartRepository
    ): MovieInteractor {
        return MovieInteractor(
            movieRepository,
            movieDaoRetrofit,
            shoppingCartRepository
        )
    }

    @Provides
    fun provideShoppingCartModel(
        shoppingCartRepository: ShoppingCartRepository
    ): CartInteractor {
        return CartInteractor(shoppingCartRepository)
    }
}