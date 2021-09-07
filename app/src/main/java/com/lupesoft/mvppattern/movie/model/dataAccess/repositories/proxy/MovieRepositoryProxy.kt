package com.lupesoft.mvppattern.movie.model.dataAccess.repositories.proxy

import android.util.Log
import com.lupesoft.mvppattern.movie.model.dataAccess.daos.MovieDao
import com.lupesoft.mvppattern.movie.model.dataAccess.entities.MovieEntity
import com.lupesoft.mvppattern.movie.model.dataAccess.repositories.MovieRepositoryRoom
import com.lupesoft.mvppattern.movie.model.repositories.MovieRepository
import com.lupesoft.mvppattern.movie.model.vo.Movie
import javax.inject.Inject

class MovieRepositoryProxy @Inject constructor(movieDao: MovieDao) : MovieRepository {

    private val movieRepositoryRoom: MovieRepositoryRoom = MovieRepositoryRoom(movieDao)

    override fun getAllMovies(): List<Movie> {
        val movies = movieRepositoryRoom.getAllMovies()
        Log.i("getAllMovies from proxy", movies.toString())
        return movies
    }

    override fun insertAll(entities: List<MovieEntity>) {
        Log.i("insertAll from proxy", entities.toString())
        movieRepositoryRoom.insertAll(entities)
    }
}