package com.aquispe.apprickandmorty.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterRemoteKeys

@Database(
    entities = [
        CharacterDbModel::class,
        CharacterRemoteKeys::class
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDbModelDao(): CharacterDbModelDao
    abstract fun remoteKeysDao(): CharacterRemoteKeysDao
}
