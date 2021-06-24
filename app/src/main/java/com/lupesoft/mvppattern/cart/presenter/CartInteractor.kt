package com.lupesoft.mvppattern.cart.presenter

import androidx.lifecycle.asLiveData
import com.lupesoft.mvppattern.movie.model.repositories.ShoppingCartRepository
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.response.Resource
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@FragmentScoped
class CartInteractor @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) {

    lateinit var onCartListener: OnCartListener

    fun getAllMoviesIntoShoppingCart() {
        val flow = flow {
            emit(
                Resource.success(data = shoppingCartRepository.getAllMoviesIntoShoppingCart())
            )
        }

        onCartListener.onGetAllMoviesIntoShoppingCart(flow
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

    fun deleteMovie(idMovie: Int) {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.deleteMovie(idMovie),
                    message = "remove_successfully"//context.getString(R.string.remove_successfully)
                )
            )
        }
        onCartListener.onDeleteMovie(flow
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

    fun deleteAllMovie() {
        val flow = flow {
            emit(
                Resource.success(
                    shoppingCartRepository.deleteAllMovie(),
                    message = "remove_all_successfully"//context.getString(R.string.remove_all_successfully)
                )
            )
        }
        onCartListener.onDeleteMovie(flow
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
}