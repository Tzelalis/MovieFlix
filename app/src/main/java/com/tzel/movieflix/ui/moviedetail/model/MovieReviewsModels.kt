package com.tzel.movieflix.ui.moviedetail.model

data class ReviewUi(
    val id: String,
    val key: String,
    val author: String,
    val content: String,
    val url: String?
) {
    val isClickable: Boolean
        get() = !url.isNullOrBlank()
}