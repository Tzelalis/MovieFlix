package com.tzel.movieflix.domain.configuration.entity

data class TemporaryRequestToken(
    val requestToken: String,
    val expiresAt: String?,
)
