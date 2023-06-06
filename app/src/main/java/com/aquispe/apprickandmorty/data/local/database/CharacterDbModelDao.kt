package com.aquispe.apprickandmorty.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel

@Dao
interface CharacterDbModelDao {

    @Query("SELECT * FROM table_character")
    fun getAllCharacters(): PagingSource<Int, CharacterDbModel>

    @Query("SELECT * FROM table_character")
    fun getCharacters(): List<CharacterDbModel>

    @Query("SELECT COUNT(id) FROM table_character")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterDbModel>)

    @Query("DELETE FROM table_character")
    fun deleteAllCharacters()
}
