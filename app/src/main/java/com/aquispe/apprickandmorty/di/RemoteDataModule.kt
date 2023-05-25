package com.aquispe.apprickandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.aquispe.apprickandmorty.data.remote.client.APIClient
import com.aquispe.apprickandmorty.data.remote.datasource.RemoteCharacterDataSource
import soy.gabimoreno.armandoquispe2.data.remote.service.CharacterService
import com.aquispe.apprickandmorty.data.repository.DefaultCharactersRepository
import com.aquispe.apprickandmorty.domain.repository.CharacterRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideHttpClient(): OkHttpClient = APIClient.getHttpClient()

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
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideContentService(
        retrofit: Retrofit,
    ): CharacterService = retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideCharacterRepository(
        remoteCharacterDataSource: RemoteCharacterDataSource,
    ): CharacterRepository = DefaultCharactersRepository(remoteCharacterDataSource)
}

private const val BASE_URL = "https://rickandmortyapi.com/api/"
