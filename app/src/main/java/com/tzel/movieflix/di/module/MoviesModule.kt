package com.tzel.movieflix.di.module

import com.tzel.movieflix.BuildConfig
import com.tzel.movieflix.data.core.AppDatabase
import com.tzel.movieflix.data.movie.MovieDao
import com.tzel.movieflix.data.movie.MovieDataSource
import com.tzel.movieflix.data.movie.MovieRepositoryImpl
import com.tzel.movieflix.di.qualifier.BaseApiOkHttpClient
import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.framework.movie.MovieApi
import com.tzel.movieflix.framework.movie.MovieDataSourceImpl
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
object MoviesModule {

    @Singleton
    @Provides
    fun provideMovieApi(
        converterFactory: MoshiConverterFactory,
        @BaseApiOkHttpClient okHttpClient: OkHttpClient
    ): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface MovieBindsModule {

    @Binds
    fun bindMovieRepositoryImpl(repo: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindMovieDataSourceImpl(dataSource: MovieDataSourceImpl): MovieDataSource
}