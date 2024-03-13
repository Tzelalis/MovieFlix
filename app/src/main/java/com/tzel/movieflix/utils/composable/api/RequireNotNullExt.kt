package com.tzel.movieflix.utils.composable.api

import retrofit2.Response

fun <T : Any> Response<T>.requireNotNull(): T {
    require(isSuccessful && body() != null)
    return body()!!
}