package com.lupesoft.mvppattern.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lupesoft.mvppattern.R
import com.lupesoft.mvppattern.databinding.ItemMovieBinding
import com.lupesoft.mvppattern.model.vo.Movie
import com.lupesoft.mvppattern.presenter.adapters.moviesholder.MoviesViewHolder
import com.lupesoft.mvppattern.presenter.adapters.moviesholder.ShoppingCartViewHolder

class MovieAdapter(
    private val actionShoppingCart: (addOrDelete: Boolean, movieId: Int) -> Unit,
    @MenuRes private val menuRes: Int
) : ListAdapter<Movie, RecyclerView.ViewHolder>(TeamDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (menuRes) {
            R.menu.popup_menu -> {
                MoviesViewHolder(
                    actionShoppingCart,
                    menuRes,
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ShoppingCartViewHolder(
                    actionShoppingCart,
                    menuRes,
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val team = getItem(position)
        when (holder) {
            is MoviesViewHolder -> holder.bind(team)
            is ShoppingCartViewHolder -> holder.bind(team)

        }
    }
}

private class TeamDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}

fun RecyclerView.setDataMovie(data: List<Movie>?, textView: TextView) {
    data?.also {
        (adapter as MovieAdapter).submitList(it)
        visibility = View.VISIBLE
        textView.visibility = View.GONE
    } ?: run {
        textView.visibility = View.VISIBLE
        visibility = View.GONE
    }
}