package com.aquispe.apprickandmorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterRemoteKeysDao {

    @Query("SELECT * FROM character_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyByCharactersById(id: Int): CharacterRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<CharacterRemoteKeys>)

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From character_remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}