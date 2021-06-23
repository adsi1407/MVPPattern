package com.lupesoft.mvppattern.presenter.dimodule

import com.lupesoft.mvppattern.model.models.MovieModel
import com.lupesoft.mvppattern.model.models.ShoppingCartModel
import com.lupesoft.mvppattern.model.networking.daos.MovieDaoRetrofit
import com.lupesoft.mvppattern.model.repositories.MovieRepository
import com.lupesoft.mvppattern.model.repositories.ShoppingCartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class ShoppingModule {

    @Provides
    fun provideMovieModel(
        movieRepository: MovieRepository,
        movieDaoRetrofit: MovieDaoRetrofit
    ): MovieModel {
        return MovieModel(movieRepository, movieDaoRetrofit)
    }

    @Provides
    fun provideShoppingCartModel(shoppingCartRepository: ShoppingCartRepository): ShoppingCartModel {
        return ShoppingCartModel(shoppingCartRepository)
    }
}