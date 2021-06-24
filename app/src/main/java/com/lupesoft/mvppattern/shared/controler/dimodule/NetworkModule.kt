package com.lupesoft.mvppattern.shared.controler.dimodule

import com.lupesoft.mvppattern.movie.model.networking.Api
import com.lupesoft.mvppattern.movie.model.networking.daos.MovieDaoRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class NetworkModule {
    @Provides
    fun provideMovieDaoRetrofit(): MovieDaoRetrofit = Api.create()
}