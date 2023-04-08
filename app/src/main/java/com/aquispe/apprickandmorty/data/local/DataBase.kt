package com.aquispe.apprickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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
