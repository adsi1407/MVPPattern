package com.lupesoft.mvppattern.movie.presenter

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.asLiveData
import com.lupesoft.mvppattern.movie.model.networking.daos.MovieDaoRetrofit
import com.lupesoft.mvppattern.movie.model.networking.dtos.toMovie
import com.lupesoft.mvppattern.movie.model.networking.dtos.toMovieEntity
import com.lupesoft.mvppattern.movie.model.networking.response.ApiEmptyResponse
import com.lupesoft.mvppattern.movie.model.networking.response.ApiErrorResponse
import com.lupesoft.mvppattern.movie.model.networking.response.ApiResponse
import com.lupesoft.mvppattern.movie.model.networking.response.ApiSuccessResponse
import com.lupesoft.mvppattern.movie.model.repositories.MovieRepository
import com.lupesoft.mvppattern.movie.model.repositories.ShoppingCartRepository
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.response.Resource
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FragmentScoped
class MovieInteractor @Inject constructor(
    private var movieRepository: MovieRepository,
    private var movieDaoRetrofit: MovieDaoRetrofit,
    private val shoppingCartRepository: ShoppingCartRepository
) {

    lateinit var onMovieListener: OnMovieListener

    private fun getAllMoviesFromNetwork(): Flow<Resource<List<Movie>>> {
        val flow = flow {
            val response = movieDaoRetrofit.getAllMovies()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    emit(Resource.loading(null, null))
                    movieRepository.insertAll(apiResponse.body.items.toMovieEntity())
                    emit(Resource.success(apiResponse.body.items.toMovie()))
                }
                is ApiEmptyResponse -> {
                    emit(
                        Resource.error(
                            null,
                            0,
                            null
                        )
                    )
                }
                is ApiErrorResponse -> {
                    emit(Resource.error(null, apiResponse.code, apiResponse.message))
                }
            }
        }

        return flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                with(exception) {
                    emit(Resource.error(null, 0, message))
                }
            }
            .flowOn(Dispatchers.IO)
    }

    fun getAllMovies() {
        val flow = flow {
            movieRepository.getAllMovies().let {
                if (it.isNotEmpty()) {
                    emit(Resource.success(it))
                } else {
                    getAllMoviesFromNetwork().collect { service ->
                        emit(service)
                    }
                }
            }
        }
        onMovieListener.onGetAllMovies(flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                emit(
                    Resource.error(
                        null,
                        0,
                        exception.message
                    )
                )
            }
            .flowOn(Dispatchers.IO)
            .asLiveData())
    }

    fun addMovie(idMovie: Int) {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.addMovie(idMovie),
                    message = "add_successfully"//context.getString(R.string.add_successfully)
                )
            )
        }
        onMovieListener.onAddMovie(flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                with(exception) {
                    val msg = when (this) {
                        is SQLiteConstraintException -> "movie_is_already"//context.getString(R.string.movie_is_already)
                        else -> null
                    }
                    emit(Resource.error(null, 0, msg))
                }
            }
            .flowOn(Dispatchers.IO)
            .asLiveData())
    }

    fun deleteMovie(idMovie: Int) {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.deleteMovie(idMovie),
                    message = "remove_successfully"//context.getString(R.string.remove_successfully)
                )
            )
        }
        onMovieListener.onDeleteMovie(flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch {
                emit(
                    Resource.error(
                        null,
                        0,
                        null
                    )
                )
            }
            .flowOn(Dispatchers.IO)
            .asLiveData())
    }

}