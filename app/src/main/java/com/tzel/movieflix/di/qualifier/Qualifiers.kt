package com.tzel.movieflix.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseApiOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedApiOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SessionIdRequired