package com.tzel.movieflix.domain.movie.entity

data class WatchProvider(
    val link: String?,
    val items: List<WatchProviderItem>,
)

data class WatchProviderItem(
    val logoPath: String,
    val providerType: WatchProvideType,
    val displayPriority: Int?,
    val providerId: Int?,
    val providerName: String?,
)

sealed class WatchProvideType(val priority: Int) {
    data object Stream : WatchProvideType(priority = 0)
    data object Rent : WatchProvideType(priority = 1)
    data object Buy : WatchProvideType(priority = 2)
    data object Free : WatchProvideType(priority = 3)
    data object Ads : WatchProvideType(priority = 4)
}