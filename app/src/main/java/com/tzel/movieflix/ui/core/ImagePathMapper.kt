package com.tzel.movieflix.ui.core

import javax.inject.Inject

class ImagePathMapper @Inject constructor() {
    operator fun invoke(imagePath: String?, imageSize: ImageSize = ImageSize.Original): String? {
        return imagePath?.let { "$IMAGE_BASE_URL${imageSize.value}$imagePath" }
    }

    companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    }
}

sealed class ImageSize(val value: String) {
    data object Original : BackdropSize("original")
    sealed class BackdropSize(value: String) : ImageSize(value) {
        data object W300 : BackdropSize("w300")
        data object W780 : BackdropSize("w780")
        data object W1280 : BackdropSize("w1280")
    }

    sealed class PosterSize(value: String) : ImageSize(value) {
        data object W92 : PosterSize("w92")
        data object W154 : PosterSize("w154")
        data object W185 : PosterSize("w185")
        data object W342 : PosterSize("w342")
        data object W500 : PosterSize("w500")
        data object W780 : PosterSize("w780")
    }

    sealed class ProfileSize(value: String) : ImageSize(value) {
        data object W45 : ProfileSize("w45")
        data object W185 : ProfileSize("w185")
        data object H632 : ProfileSize("h632")
    }
}