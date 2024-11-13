package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteVideoItem
import com.tzel.movieflix.data.movie.model.RemoteVideoResponse
import com.tzel.movieflix.domain.movie.entity.VideoItem
import com.tzel.movieflix.domain.movie.entity.VideoSite
import com.tzel.movieflix.domain.movie.entity.VideoType
import javax.inject.Inject

class RemoteVideoMapper @Inject constructor() {
    operator fun invoke(response: RemoteVideoResponse?): List<VideoItem> {
        return response?.results?.mapNotNull { invoke(it) } ?: emptyList()
    }

    private operator fun invoke(video: RemoteVideoItem?): VideoItem? {
        if (video == null) return null

        val videoType = mapVideoType(video.type) ?: return null
        val videoSite = mapVideoSite(video.site) ?: return null
        val key = video.key ?: return null

        return VideoItem(
            id = video.id,
            type = videoType,
            site = videoSite,
            key = key,
            name = video.name,
            size = video.size,
            official = video.official ?: false,
            iso6391 = video.iso6391,
            iso31661 = video.iso31661
        )
    }

    private fun mapVideoType(type: String?): VideoType? {
        return when (type) {
            "Trailer" -> VideoType.Trailer
            "Teaser" -> VideoType.Teaser
            else -> null
        }
    }

    private fun mapVideoSite(site: String?): VideoSite? {
        return when (site) {
            "YouTube" -> VideoSite.YouTube
            else -> null
        }
    }
}