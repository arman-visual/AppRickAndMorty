package com.aquispe.apprickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDbModelDao {

    @Query("SELECT * FROM CharacterDbModel")
    fun getAllCharacters(): PagingSource<Int, CharacterDbModel>

    @Query("SELECT * FROM CharacterDbModel")
    fun getCharacters(): List<CharacterDbModel>

    @Query("SELECT COUNT(id) FROM CharacterDbModel")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterDbModel>)

    @Query("DELETE FROM CharacterDbModel")
    fun deleteAllCharacters()
}