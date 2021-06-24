package com.lupesoft.mvppattern.movie.model.networking.dtos

import com.lupesoft.mvppattern.movie.model.networking.vos.MovieVo
import com.lupesoft.mvppattern.movie.model.dataAccess.entities.MovieEntity
import com.lupesoft.mvppattern.movie.model.vo.Movie

fun List<MovieVo>.toMovieEntity(): List<MovieEntity> {
    return map {
        MovieEntity(
            it.id,
            it.adult,
            it.backdropPath,
            it.mediaType,
            it.originalLanguage,
            it.originalTitle,
            it.overview,
            it.popularity,
            it.posterPath,
            it.title,
            it.video,
            it.voteAverage,
            it.voteCount
        )
    }
}

fun List<MovieVo>.toMovie(): List<Movie> {
    return map {
        Movie(
            it.adult,
            it.backdropPath,
            it.id,
            it.mediaType,
            it.originalLanguage,
            it.originalTitle,
            it.overview,
            it.popularity,
            it.posterPath,
            it.title,
            it.video,
            it.voteAverage,
            it.voteCount
        )
    }
}