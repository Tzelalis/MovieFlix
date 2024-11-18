package com.tzel.movieflix.data.core.mapper

import com.tzel.movieflix.data.core.RemoteStatusResponse
import javax.inject.Inject

class RemoteStatusMapper @Inject constructor() {
    operator fun invoke(remote: RemoteStatusResponse?): Boolean {
        return remote?.success == true
    }
}