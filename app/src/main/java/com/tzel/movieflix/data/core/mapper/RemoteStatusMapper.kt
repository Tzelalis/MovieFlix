package com.tzel.movieflix.data.core.mapper

import com.tzel.movieflix.data.core.RemoteStatusResponse
import com.tzel.movieflix.domain.core.Mapper
import javax.inject.Inject

class RemoteStatusMapper @Inject constructor() : Mapper {
    operator fun invoke(remote: RemoteStatusResponse?): Boolean? {
        return when (remote?.status) {
            ADDED_STATUS_CODE -> true
            REMOVED_STATUS_CODE -> false
            else -> null
        }
    }

    companion object {
        private const val ADDED_STATUS_CODE = 1
        private const val REMOVED_STATUS_CODE = 13
    }
}