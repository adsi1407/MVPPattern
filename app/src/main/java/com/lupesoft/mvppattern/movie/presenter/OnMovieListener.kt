package com.lupesoft.mvppattern.movie.presenter

import androidx.lifecycle.LiveData
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.response.Resource

interface OnMovieListener {
    fun onGetAllMovies(allMovies: LiveData<Resource<List<Movie>>>)
    fun onAddMovie(success: LiveData<Resource<Long>>)
    fun onDeleteMovie(success: LiveData<Resource<Int>>)
}