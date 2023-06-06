package com.aquispe.apprickandmorty.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterRemoteKeys

@Dao
interface CharacterRemoteKeysDao {

    @Query("SELECT * FROM character_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyById(id: Int): CharacterRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<CharacterRemoteKeys>)

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM character_remote_keys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
