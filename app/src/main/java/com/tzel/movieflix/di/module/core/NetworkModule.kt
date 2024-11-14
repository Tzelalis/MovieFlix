package com.tzel.movieflix.di.module.core

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tzel.movieflix.BuildConfig
import com.tzel.movieflix.data.core.AppDatabase
import com.tzel.movieflix.di.module.interceptor.AuthInterceptor
import com.tzel.movieflix.di.module.interceptor.NetworkConnectivityInterceptor
import com.tzel.movieflix.di.qualifier.BaseApiOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .allEnabledTlsVersions()
            .allEnabledCipherSuites()
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
    @BaseApiOkHttpClient
    fun provideBaseApiOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectionSpec: ConnectionSpec,
        authInterceptor: AuthInterceptor,
        connectivityInterceptor: NetworkConnectivityInterceptor,
        db: AppDatabase
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
            .addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url

                val sessionId = runBlocking { db.configurationDao().getConfiguration()?.sessionId } ?: return@addInterceptor chain.proceed(original)

                val urlWithSession = originalUrl.newBuilder()
                    .addQueryParameter("session_id", sessionId)
                    .build()

                val requestWithSession = original.newBuilder()
                    .url(urlWithSession)
                    .build()

                chain.proceed(requestWithSession)
            }
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }
}

