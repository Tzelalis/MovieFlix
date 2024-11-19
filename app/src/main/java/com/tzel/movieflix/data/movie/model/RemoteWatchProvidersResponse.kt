package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteWatchProvidersResponse(
    val id: Int?,
    val results: Map<String?, RemoteWatchProvider?>?,
)

@JsonClass(generateAdapter = true)
data class RemoteWatchProvider(
    val link: String?,
    val buy: List<RemoteWatchProviderItem?>?,
    val flatrate: List<RemoteWatchProviderItem?>?,
    val rent: List<RemoteWatchProviderItem?>?
)

@JsonClass(generateAdapter = true)
data class RemoteWatchProviderItem(
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "display_priority") val displayPriority: Int?,
    @Json(name = "provider_id") val providerId: Int?,
    @Json(name = "provider_name") val providerName: String?
)