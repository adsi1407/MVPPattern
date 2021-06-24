package com.lupesoft.mvppattern.movie.model.repositories

import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.movie.model.dataAccess.entities.MovieEntity

interface MovieRepository {

    fun getAllMovies(): List<Movie>

    fun insertAll(entities: List<MovieEntity>)
}