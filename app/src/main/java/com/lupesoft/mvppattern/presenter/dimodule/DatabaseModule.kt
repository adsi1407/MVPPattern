package com.lupesoft.mvppattern.presenter.dimodule

import android.content.Context
import com.lupesoft.mvppattern.model.dataAccess.AppDataBase
import com.lupesoft.mvppattern.model.dataAccess.daos.MovieDao
import com.lupesoft.mvppattern.model.dataAccess.daos.ShoppingCartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase =
        AppDataBase.getInstance(context)

    @Provides
    fun provideMovieDao(appDataBase: AppDataBase): MovieDao = appDataBase.movieDao()

    @Provides
    fun provideShoppingCartDao(appDataBase: AppDataBase): ShoppingCartDao =
        appDataBase.shoppingCartDao()
}