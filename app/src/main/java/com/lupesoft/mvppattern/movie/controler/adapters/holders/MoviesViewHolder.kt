package com.lupesoft.mvppattern.movie.controler.adapters.holders

import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lupesoft.mvppattern.shared.controler.extensions.loadImage
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.databinding.ItemMovieBinding
import com.lupesoft.mvppattern.movie.model.vo.Movie

class MoviesViewHolder(
    private val actionShoppingCart: (addOrDelete: Boolean, movieId: Int) -> Unit,
    @MenuRes private val menuRes: Int,
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    private fun showMenu(v: View, movie: Movie) {
        PopupMenu(v.context, v).apply {
            menuInflater.inflate(menuRes, menu)
            setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.add_movie -> {
                        actionShoppingCart(true, movie.id)
                        true
                    }
                    R.id.delete_movie -> {
                        actionShoppingCart(false, movie.id)
                        true
                    }
                    R.id.detail_movie -> {
                        navigate(movie, v)
                        true
                    }
                    else -> false
                }
            }
        }.also { it.show() }
    }

    private fun navigate(elem: Movie, view: View) {
        view.findNavController().navigate(
            R.id.action_allMoviesFragment_to_aboutOfMovieFragment, bundleOf(
                "movie" to elem
            )
        )
    }

    fun bind(item: Movie) {
        binding.apply {
            itemImage.loadImage(item.posterPath)
            itemName.text = item.title
            itemStadium.text = item.popularity.toString()
            cardMovie.setOnClickListener { v ->
                showMenu(v, item)
            }
        }
    }
}