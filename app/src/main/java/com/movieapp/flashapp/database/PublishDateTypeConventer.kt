package com.movieapp.flashapp.database

import androidx.room.TypeConverter
import com.movieapp.flashapp.data.PublishDate

class PublishDateTypeConventer {
    @TypeConverter
    fun fromPublishDate(date: PublishDate): String {
        return "${date.year}-${date.month}-${date.day}"
    }

    @TypeConverter
    fun toPublishDate(dateString: String): PublishDate {
        val dateParts = dateString.split("-")
        return PublishDate(
            day = dateParts[2].toInt(),
            month = dateParts[1].toInt(),
            year = dateParts[0].toInt()
        )
    }
}