package com.tzel.movieflix.di.module

import com.tzel.movieflix.BuildConfig
import com.tzel.movieflix.data.auth.AuthDataSource
import com.tzel.movieflix.data.auth.AuthRepositoryImpl
import com.tzel.movieflix.data.core.AppDatabase
import com.tzel.movieflix.di.qualifier.AuthenticatedApiOkHttpClient
import com.tzel.movieflix.domain.auth.AuthRepository
import com.tzel.movieflix.framework.auth.AuthApi
import com.tzel.movieflix.framework.auth.AuthDao
import com.tzel.movieflix.framework.auth.AuthDataSourceImpl
import com.tzel.movieflix.framework.auth.AuthenticationBroadcast
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
object AuthModule {

    @Singleton
    @Provides
    fun provideMovieApi(
        converterFactory: MoshiConverterFactory,
        @AuthenticatedApiOkHttpClient okHttpClient: OkHttpClient
    ): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthDao(appDatabase: AppDatabase): AuthDao {
        return appDatabase.authDao()
    }

    @Singleton
    @Provides
    fun provideAuthBroadcast(): AuthenticationBroadcast {
        return AuthenticationBroadcast()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface AuthBindsModule {

    @Binds
    fun bindAuthRepositoryImpl(repo: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindAuthDataSourceImpl(dataSource: AuthDataSourceImpl): AuthDataSource
}