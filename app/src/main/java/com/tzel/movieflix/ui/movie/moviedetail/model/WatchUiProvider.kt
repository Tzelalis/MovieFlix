package com.tzel.movieflix.ui.movie.moviedetail.model

import com.tzel.movieflix.domain.movie.entity.WatchProvideType


data class WatchUiProvider(
    val link: String?,
    val items: List<WatchProviderUiItem>,
)

data class WatchProviderUiItem(
    val logoPath: String,
    val providerType: WatchProvideType,
    val displayPriority: Int?,
    val providerId: Int?,
    val providerName: String?,
) {
    val key: String
        get() = "type:${providerType}id:$providerId"
}
