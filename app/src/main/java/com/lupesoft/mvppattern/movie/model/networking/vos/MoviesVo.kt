package com.lupesoft.mvppattern.movie.model.networking.vos

import com.google.gson.annotations.SerializedName

data class MoviesVo(
        @field:SerializedName("created_by") val createdBy: String,
        @field:SerializedName("description") val description: String,
        @field:SerializedName("favorite_count") val favoriteCount: Int,
        @field:SerializedName("id") val id: String,
        @field:SerializedName("items") val items: List<MovieVo>,
        @field:SerializedName("itemCount") val itemCount: Int,
        @field:SerializedName("iso_639_1") val iso_639_1: String,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("poster_path") val posterPath: String)
