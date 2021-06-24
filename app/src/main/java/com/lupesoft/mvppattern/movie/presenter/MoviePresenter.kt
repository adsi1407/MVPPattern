package com.lupesoft.mvppattern.movie.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.lupesoft.mvppattern.movie.model.vo.Movie
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.Status
import com.lupesoft.mvppattern.shared.model.dataAccess.utils.response.Resource
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MoviePresenter @Inject constructor(
    private val movieInteractor: MovieInteractor
) : OnMovieListener {

    var movieView: MovieView? = null
        set(value) {
            field = value
            movieInteractor.onMovieListener = this
        }

    fun addMovie(movieId: Int) {
        movieInteractor.addMovie(movieId)
    }

    fun deleteMovie(movieId: Int) {
        movieInteractor.deleteMovie(movieId)
    }

    fun getAllMovies() {
        movieInteractor.getAllMovies()
    }

    private fun <T> actionEventAddOrRemove(result: Resource<T>) {
        when (result.status) {
            Status.SUCCESS -> movieView?.hideProgress()
            Status.LOADING -> movieView?.showProgress()
            Status.ERROR -> movieView?.hideProgress()
        }
        if (result.status != Status.LOADING) {
            movieView?.showError(result.message)
        }
    }


    override fun onGetAllMovies(allMovies: LiveData<Resource<List<Movie>>>) {
        allMovies.observe(movieView!!.getLifecycleOwner(), Observer { result ->
            when (result.status) {
                Status.LOADING -> movieView?.showProgress()
                Status.SUCCESS -> {
                    movieView?.setDataMovie(result.data)
                    movieView?.hideProgress()
                }
                Status.ERROR -> {
                    movieView?.setDataMovie(null)
                    movieView?.hideProgress()
                    movieView?.showError("Error")
                }
            }
        })
    }

    override fun onAddMovie(success: LiveData<Resource<Long>>) {
        success.observe(movieView!!.getLifecycleOwner(), Observer { result ->
            actionEventAddOrRemove(result)
        })
    }

    override fun onDeleteMovie(success: LiveData<Resource<Int>>) {
        success.observe(movieView!!.getLifecycleOwner(), Observer { result ->
            actionEventAddOrRemove(result)
        })
    }
}