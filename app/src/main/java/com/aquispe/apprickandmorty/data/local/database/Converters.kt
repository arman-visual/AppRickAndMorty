package com.aquispe.apprickandmorty.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.aquispe.apprickandmorty.data.local.database.entities.LocationDbModel
import com.aquispe.apprickandmorty.data.local.database.entities.OriginDbModel

class Converters {

    @TypeConverter
    fun stringToLocation(s: String): LocationDbModel = s.toObject()

    @TypeConverter
    fun locationToString(location: LocationDbModel): String = location.fromObject()

    @TypeConverter
    fun stringToOrigin(s: String): OriginDbModel = s.toObject()

    @TypeConverter
    fun originToString(origin: OriginDbModel): String = origin.fromObject()

    @TypeConverter
    fun fromString(string: String?): List<String> {
        if (string == null) {
            return emptyList()
        }
        return string.split(",")
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        if (list == null) {
            return ""
        }
        return list.joinToString(",")
    }

    private inline fun <reified T> String.toObject(): T {
        return Gson().fromJson(
            this,
            object : com.google.gson.reflect.TypeToken<T>() {}.type
        )
    }

    private inline fun <reified T> T.fromObject(): String {
        return Gson().toJson(this)
    }
}
