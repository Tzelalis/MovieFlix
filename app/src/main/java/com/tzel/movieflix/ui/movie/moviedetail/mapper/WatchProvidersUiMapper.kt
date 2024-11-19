package com.tzel.movieflix.ui.movie.moviedetail.mapper

import com.tzel.movieflix.domain.movie.entity.WatchProvider
import com.tzel.movieflix.domain.movie.entity.WatchProviderItem
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchProviderUiItem
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchUiProvider
import javax.inject.Inject

class WatchProvidersUiMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) {
    operator fun invoke(providers: Map<String, WatchProvider>, region: String): WatchUiProvider? {
        return invoke(providers[region])?.takeUnless { it.items.isEmpty() }
    }

    operator fun invoke(provider: WatchProvider?): WatchUiProvider? {
        val items = provider?.items?.mapNotNull { mapProviderItem(it) } ?: return null

        return WatchUiProvider(
            link = provider.link,
            items = items,
        )
    }

    private fun mapProviderItem(item: WatchProviderItem): WatchProviderUiItem? {
        val imageUrl = imagePathMapper(item.logoPath) ?: return null

        return WatchProviderUiItem(
            logoPath = imageUrl,
            providerType = item.providerType,
            displayPriority = item.displayPriority,
            providerId = item.providerId,
            providerName = item.providerName,
        )
    }
}