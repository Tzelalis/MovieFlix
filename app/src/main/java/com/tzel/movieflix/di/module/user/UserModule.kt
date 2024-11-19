package com.tzel.movieflix.di.module.user

import com.tzel.movieflix.BuildConfig
import com.tzel.movieflix.data.user.UserDataSource
import com.tzel.movieflix.data.user.UserRepositoryImpl
import com.tzel.movieflix.di.module.interceptor.AuthInterceptor
import com.tzel.movieflix.di.module.interceptor.NetworkConnectivityInterceptor
import com.tzel.movieflix.di.module.interceptor.SessionInterceptor
import com.tzel.movieflix.di.qualifier.AuthenticatedApiOkHttpClient
import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.framework.user.UserApi
import com.tzel.movieflix.framework.user.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Singleton
    @Provides
    @AuthenticatedApiOkHttpClient
    fun provideBaseApiOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectionSpec: ConnectionSpec,
        authInterceptor: AuthInterceptor,
        connectivityInterceptor: NetworkConnectivityInterceptor,
        sessionInterceptor: SessionInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec)).apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(httpLoggingInterceptor)
                }
            }
            .addInterceptor(sessionInterceptor)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApi(
        converterFactory: MoshiConverterFactory,
        @AuthenticatedApiOkHttpClient okHttpClient: OkHttpClient,
    ): UserApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(UserApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface UserBindsModule {

    @Binds
    fun bindUserRepositoryImpl(repo: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserDataSourceImpl(dataSource: UserDataSourceImpl): UserDataSource
}