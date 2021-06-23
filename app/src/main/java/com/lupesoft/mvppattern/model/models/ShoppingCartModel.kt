package com.lupesoft.mvppattern.model.models

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lupesoft.mvppattern.model.dataAccess.utils.response.Resource
import com.lupesoft.mvppattern.model.repositories.ShoppingCartRepository
import com.lupesoft.mvppattern.model.vo.Movie
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@FragmentScoped
class ShoppingCartModel @Inject constructor(private val shoppingCartRepository: ShoppingCartRepository) {

    var movie: Movie? = null

    fun getAllMoviesIntoShoppingCart(): LiveData<Resource<List<Movie>>> {
        val flow = flow {
            emit(
                Resource.success(data = shoppingCartRepository.getAllMoviesIntoShoppingCart())
            )
        }
        return flow
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
            .asLiveData()
    }

    fun addMovie(idMovie: Int): LiveData<Resource<Long>> {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.addMovie(idMovie),
                    message = "add_successfully"//context.getString(R.string.add_successfully)
                )
            )
        }
        return flow
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
            .asLiveData()
    }

    fun deleteMovie(idMovie: Int): LiveData<Resource<Int>> {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.deleteMovie(idMovie),
                    message = "remove_successfully"//context.getString(R.string.remove_successfully)
                )
            )
        }
        return flow
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
            .asLiveData()
    }

    fun deleteAllMovie(): LiveData<Resource<Int>> {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.deleteAllMovie(),
                    message = "remove_all_successfully"//context.getString(R.string.remove_all_successfully)
                )
            )
        }
        return flow
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
            .asLiveData()
    }
}