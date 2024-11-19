package com.tzel.movieflix.utils.ext

import okhttp3.Request
import retrofit2.Invocation
import retrofit2.Response

fun <T : Any> Response<T>.requireNotNull(): T {
    require(isSuccessful && body() != null)
    return body()!!
}

fun <T : Annotation> Request.getAnnotation(annotationClass: Class<T>): T? {
    return this.tag(Invocation::class.java)?.method()?.getAnnotation(annotationClass)
}