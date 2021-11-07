package com.example.dataBase

import androidx.room.TypeConverter
import java.util.*

class ToDoTypeConverters {

    @TypeConverter

    fun fromDate(date: Date?):Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate (millisSinceEpoch:Long?):Date?{
        return millisSinceEpoch?.let {
            Date(it)
        }
    }
    @TypeConverter
    fun fromUUID(uudi:UUID?):String? {
        return uudi?.toString()
    }
    @TypeConverter
    fun toUUDI (uudi: String?):UUID?{
        return UUID.fromString(uudi)
    }


}