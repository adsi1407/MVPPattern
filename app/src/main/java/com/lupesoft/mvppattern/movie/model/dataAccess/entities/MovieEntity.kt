package com.lupesoft.mvppattern.movie.model.dataAccess.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MovieEntity(
        @PrimaryKey
        @ColumnInfo(name = "idMovie")
        val id: Int,
        val adult: Boolean,
        val backdropPath: String,
        val mediaType: String,
        val originalLanguage: String,
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val title: String,
        val video: Boolean,
        val voteAverage: Double,
        val voteCount: Int
)