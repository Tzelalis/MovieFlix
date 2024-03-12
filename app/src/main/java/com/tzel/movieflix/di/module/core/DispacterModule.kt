package com.tzel.movieflix.di.module.core

import com.tzel.movieflix.domain.core.dispatcher.entity.ExecuteOn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    fun provideExecuteOn(): ExecuteOn = ExecuteOn(
        backgroundDispatcher = Dispatchers.IO,
        defaultDispatcher = Dispatchers.Default,
        mainDispatcher = Dispatchers.Main
    )
}