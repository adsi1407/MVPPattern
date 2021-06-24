package com.lupesoft.mvppattern.movie.model.dataAccess.repositories

import com.lupesoft.mvppattern.movie.model.repositories.MovieRepository
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.movie.model.dataAccess.daos.MovieDao
import com.lupesoft.mvppattern.movie.model.dataAccess.dtos.toDomainModel
import com.lupesoft.mvppattern.movie.model.dataAccess.entities.MovieEntity
import javax.inject.Inject

class MovieRepositoryRoom @Inject constructor(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies().toDomainModel()
    }

    override fun insertAll(entities: List<MovieEntity>) {
        return movieDao.insertAll(entities)
    }
}