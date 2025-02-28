package com.tzel.movieflix.ui.moviedetail.model

import androidx.annotation.StringRes
import com.tzel.movieflix.R


data class WatchUiProvider(
    val link: String?,
    val items: List<WatchProviderUiItem>,
){
    val hasLink = !link.isNullOrBlank()
}

data class WatchProviderUiItem(
    val logoPath: String,
    val providerType: WatchProviderUiType,
    val displayPriority: Int?,
    val providerId: Int?,
    val providerName: String?,
) {
    val key: String
        get() = "type:${providerType}id:$providerId"
}

sealed class WatchProviderUiType(@StringRes val nameRes: Int) {
    data object Buy : WatchProviderUiType(nameRes = R.string.home_detail_watch_provider_buy_name)
    data object Rent : WatchProviderUiType(nameRes = R.string.home_detail_watch_provider_rent_name)
    data object Stream : WatchProviderUiType(nameRes = R.string.home_detail_watch_provider_flatrate_name)
    data object Free : WatchProviderUiType(nameRes = R.string.home_detail_watch_provider_free_name)
    data object Ads : WatchProviderUiType(nameRes = R.string.home_detail_watch_provider_ads_name)

}
