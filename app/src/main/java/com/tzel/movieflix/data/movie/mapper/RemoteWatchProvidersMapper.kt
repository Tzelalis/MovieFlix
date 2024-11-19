package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteWatchProvider
import com.tzel.movieflix.data.movie.model.RemoteWatchProviderItem
import com.tzel.movieflix.data.movie.model.RemoteWatchProvidersResponse
import com.tzel.movieflix.domain.movie.entity.WatchProvideType
import com.tzel.movieflix.domain.movie.entity.WatchProvider
import com.tzel.movieflix.domain.movie.entity.WatchProviderItem
import javax.inject.Inject

class RemoteWatchProvidersMapper @Inject constructor() {
    operator fun invoke(response: RemoteWatchProvidersResponse?): Map<String, WatchProvider> {
        return response?.results
            ?.filterNot { pair -> pair.key == null || pair.value == null }
            ?.mapKeys { it.key!! }
            ?.mapValues { (_, value) -> invoke(value!!) }
            ?: emptyMap()
    }

    operator fun invoke(item: RemoteWatchProvider): WatchProvider {
        val buyProviders = item.buy?.mapNotNull { invoke(it, WatchProvideType.Buy) } ?: emptyList()
        val rentProviders = item.rent?.mapNotNull { invoke(it, WatchProvideType.Rent) } ?: emptyList()
        val faltrateProviders = item.flatrate?.mapNotNull { invoke(it, WatchProvideType.Stream) } ?: emptyList()
        val adsProviders = item.flatrate?.mapNotNull { invoke(it, WatchProvideType.Ads) } ?: emptyList()
        val freeProviders = item.flatrate?.mapNotNull { invoke(it, WatchProvideType.Free) } ?: emptyList()

        val providers = buyProviders + rentProviders + faltrateProviders + adsProviders + freeProviders
        val sortedProviders = providers
            .sortedWith(compareBy({ it.providerType.priority }, { it.displayPriority }, { it.providerName }))
            .distinctBy { it.providerId }

        return WatchProvider(
            link = item.link,
            items = sortedProviders
        )
    }

    operator fun invoke(item: RemoteWatchProviderItem?, providerType: WatchProvideType): WatchProviderItem? {
        if (item?.logoPath == null) return null

        return WatchProviderItem(
            logoPath = item.logoPath,
            displayPriority = item.displayPriority,
            providerId = item.providerId,
            providerName = item.providerName,
            providerType = providerType
        )
    }
}