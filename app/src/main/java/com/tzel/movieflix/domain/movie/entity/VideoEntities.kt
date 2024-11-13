package com.tzel.movieflix.domain.movie.entity

data class VideoItem(
    val type: VideoType,
    val site: VideoSite,
    val key: String,
    val id: String?,
    val name: String?,
    val size: Int?,
    val official: Boolean,
    val iso6391: String?,
    val iso31661: String?,
)

sealed class VideoType {
    data object Trailer : VideoType()
    data object Teaser : VideoType()
}

sealed class VideoSite {
    data object YouTube : VideoSite()
}