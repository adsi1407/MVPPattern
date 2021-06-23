package com.lupesoft.mvppattern.model.repositories

import com.lupesoft.mvppattern.model.vo.Movie
import com.lupesoft.mvppattern.model.dataAccess.entities.MovieEntity

interface MovieRepository {

    fun getAllMovies(): List<Movie>

    fun insertAll(entities: List<MovieEntity>)
}