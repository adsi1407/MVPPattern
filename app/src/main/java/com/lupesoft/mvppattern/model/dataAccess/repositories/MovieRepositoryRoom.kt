package com.lupesoft.mvppattern.model.dataAccess.repositories

import com.lupesoft.mvppattern.model.repositories.MovieRepository
import com.lupesoft.mvppattern.model.vo.Movie
import com.lupesoft.mvppattern.model.dataAccess.daos.MovieDao
import com.lupesoft.mvppattern.model.dataAccess.dtos.toDomainModel
import com.lupesoft.mvppattern.model.dataAccess.entities.MovieEntity
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