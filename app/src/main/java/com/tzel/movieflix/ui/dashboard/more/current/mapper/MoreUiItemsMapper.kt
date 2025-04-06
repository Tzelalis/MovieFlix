package com.tzel.movieflix.ui.dashboard.more.current.mapper

import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiItem
import javax.inject.Inject

class MoreUiItemsMapper @Inject constructor() : Mapper {
    operator fun invoke(): List<MoreUiItem> {
        return listOf(
            MoreUiItem.Language,
            MoreUiItem.DashboardSettings
        )
    }
}