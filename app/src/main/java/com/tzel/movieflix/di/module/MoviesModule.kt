package com.tzel.movieflix.di.module

import com.tzel.movieflix.data.movie.MovieDataSource
import com.tzel.movieflix.data.movie.MovieRepositoryImpl
import com.tzel.movieflix.framework.movie.MovieRepository
import com.tzel.movieflix.framework.movie.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

}

@Module
@InstallIn(SingletonComponent::class)
interface MovieBindsModule {

    @Binds
    fun bindMovieRepositoryImpl(repo: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindMovieDataSourceImpl(dataSource: MovieDataSourceImpl): MovieDataSource
}