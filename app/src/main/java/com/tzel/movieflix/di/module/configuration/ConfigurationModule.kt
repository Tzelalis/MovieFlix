package com.tzel.movieflix.di.module.configuration

import com.tzel.movieflix.BuildConfig
import com.tzel.movieflix.data.configuration.ConfigurationDataSource
import com.tzel.movieflix.data.configuration.ConfigurationRepositoryImpl
import com.tzel.movieflix.data.core.AppDatabase
import com.tzel.movieflix.di.qualifier.BaseApiOkHttpClient
import com.tzel.movieflix.domain.configuration.ConfigurationRepository
import com.tzel.movieflix.framework.configuration.ConfigurationApi
import com.tzel.movieflix.framework.configuration.ConfigurationDao
import com.tzel.movieflix.framework.configuration.ConfigurationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Singleton
    @Provides
    fun provideMovieApi(
        converterFactory: MoshiConverterFactory,
        @BaseApiOkHttpClient okHttpClient: OkHttpClient
    ): ConfigurationApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(ConfigurationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): ConfigurationDao {
        return appDatabase.configurationDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface MovieBindsModule {

    @Binds
    fun bindMovieRepositoryImpl(repo: ConfigurationRepositoryImpl): ConfigurationRepository

    @Binds
    fun bindMovieDataSourceImpl(dataSource: ConfigurationDataSourceImpl): ConfigurationDataSource
}