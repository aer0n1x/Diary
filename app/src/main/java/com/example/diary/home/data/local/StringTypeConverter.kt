package com.example.diary.home.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

object StringListTypeConverter {
    @TypeConverter
    inline fun <reified T> set(value: String): T {
        return Json.decodeFromString<T>(value)
    }

    @TypeConverter
    inline fun <reified T> get(value: T): String {
        return Json.encodeToString(value)
    }
}