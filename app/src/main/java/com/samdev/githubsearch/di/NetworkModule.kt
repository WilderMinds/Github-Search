package com.samdev.githubsearch.di

import com.samdev.githubsearch.AppConstants
import com.samdev.githubsearch.BuildConfig
import com.samdev.githubsearch.data.network.ApiService
import com.samdev.githubsearch.data.network.AuthInterceptor
import com.samdev.githubsearch.data.preference.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Sam
 * Created 27/11/2021 at 12:29 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        prefManager: PrefManager
    ) = AuthInterceptor(prefManager)


    @Singleton
    @Provides
    fun provideOKHTTP(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(
        ApiService::class.java
    )
}