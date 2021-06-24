package com.lupesoft.mvppattern.movie.model.vo

import java.io.Serializable

class Movie(
    adult: Boolean,
    backdropPath: String,
    id: Int,
    mediaType: String,
    originalLanguage: String,
    originalTitle: String,
    overview: String,
    popularity: Double,
    posterPath: String,
    title: String,
    video: Boolean,
    voteAverage: Double,
    voteCount: Int
) : Serializable {

    var adult: Boolean = adult
        private set
    var backdropPath: String = backdropPath
        private set
    var id: Int = id
        private set
    var mediaType: String = mediaType
        private set
    var originalLanguage: String = originalLanguage
        private set
    var originalTitle: String = originalTitle
        private set
    var overview: String = overview
        private set
    var popularity: Double = popularity
        private set
    var posterPath: String = posterPath
        private set
    var title: String = title
        private set
    var video: Boolean = video
        private set
    var voteAverage: Double = voteAverage
        private set
    var voteCount: Int = voteCount
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (adult != other.adult) return false
        if (backdropPath != other.backdropPath) return false
        if (id != other.id) return false
        if (mediaType != other.mediaType) return false
        if (originalLanguage != other.originalLanguage) return false
        if (originalTitle != other.originalTitle) return false
        if (overview != other.overview) return false
        if (popularity != other.popularity) return false
        if (posterPath != other.posterPath) return false
        if (title != other.title) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false
        if (voteCount != other.voteCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = adult.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + id
        result = 31 * result + mediaType.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + voteCount
        return result
    }


}