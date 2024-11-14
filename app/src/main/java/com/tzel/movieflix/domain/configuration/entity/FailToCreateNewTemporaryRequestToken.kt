package com.tzel.movieflix.domain.configuration.entity

data object FailToCreateNewTemporaryRequestToken : Exception() {
    private fun readResolve(): Any = FailToCreateNewTemporaryRequestToken
}