package com.lupesoft.mvppattern.model.networking.daos

import com.lupesoft.mvppattern.model.networking.vos.MoviesVo
import com.lupesoft.mvppattern.BuildConfig.ApiKey
import retrofit2.Response
import retrofit2.http.GET

interface MovieDaoRetrofit {
    @GET("list/3?api_key=${ApiKey}&language=en-US")
    suspend fun getAllMovies(): Response<MoviesVo>
}