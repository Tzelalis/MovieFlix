package com.tzel.movieflix.domain.core.dispatcher.entity

import com.tzel.movieflix.di.qualifier.BackgroundDispatcher
import com.tzel.movieflix.di.qualifier.DefaultDispatcher
import com.tzel.movieflix.di.qualifier.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExecuteOn @Inject constructor(
    @BackgroundDispatcher private val backgroundDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend fun <T> background(block: suspend CoroutineScope.() -> T): T {
        return withContext(backgroundDispatcher) {
            block()
        }
    }

    suspend fun <T> default(block: suspend CoroutineScope.() -> T): T {
        return withContext(defaultDispatcher) {
            block()
        }
    }

    suspend fun <T> main(block: suspend CoroutineScope.() -> T): T {
        return withContext(mainDispatcher) {
            block()
        }
    }
}