package com.aquispe.apprickandmorty.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.aquispe.apprickandmorty.data.local.database.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import com.aquispe.apprickandmorty.data.local.datamapper.toCharacterDbModel
import com.aquispe.apprickandmorty.domain.model.Character
import javax.inject.Inject

class LocalCharacterDataSource @Inject constructor(
    characterDatabase: CharacterDatabase,
) {
    private val characterDbModelDao = characterDatabase.characterDbModelDao()

    suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        characterDbModelDao.count() <= 0
    }

    suspend fun saveCharacters(characters: List<Character>) = withContext(Dispatchers.IO) {
        characterDbModelDao.insertCharacters(characters.map { it.toCharacterDbModel() })
    }

    suspend fun getCharacters(): List<CharacterDbModel> = withContext(Dispatchers.IO) {
        characterDbModelDao.getCharacters()
    }

    suspend fun reset() = withContext(Dispatchers.IO) {
        characterDbModelDao.deleteAllCharacters()
    }
}
