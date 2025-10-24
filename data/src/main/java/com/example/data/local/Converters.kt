package com.example.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(episodes: List<String>): String {
        return episodes.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split(",")
    }
}
