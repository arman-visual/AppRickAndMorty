package com.aquispe.apprickandmorty.di

import com.aquispe.apprickandmorty.data.remote.client.APIClient
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    fun provideHttpClient(): OkHttpClient = APIClient.createHttpClient()

    @Provides
    @Singleton
    @BaseUrl
    fun provideUrl(): String = BASE_URL

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideContentService(
        retrofit: Retrofit,
    ): CharacterService = retrofit.create(CharacterService::class.java)
}

private const val BASE_URL = "https://rickandmortyapi.com"