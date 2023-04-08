package com.aquispe.apprickandmorty.data.repository

import com.aquispe.apprickandmorty.data.remote.datasource.RemoteCharacterDataSource
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteCharacterDataSource: RemoteCharacterDataSource)
{
    suspend fun getCharactersById(id: Int) = remoteCharacterDataSource.getCharacterById(id)
}