package com.lupesoft.mvppattern.model.dataAccess.dtos

import com.lupesoft.mvppattern.model.vo.Movie
import com.lupesoft.mvppattern.model.dataAccess.entities.MovieEntity

fun List<Movie>.toMovieEntity(): List<MovieEntity> {
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
                it.voteCount)
    }
}

fun List<MovieEntity>.toDomainModel(): List<Movie> {
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
                it.voteCount)
    }
}