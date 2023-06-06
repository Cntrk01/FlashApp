package com.movieapp.flashapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData

@Database(entities = [ColorLightData::class,FlashLightData::class,SosAlertsData::class], version = 1, exportSchema = false)
@TypeConverters(PublishDateTypeConventer::class)
abstract class FlashlightDatabase : RoomDatabase() {
    abstract fun colorLightDao(): ColorLightDao
    abstract fun flashLightDao() : FlashLightDao
    abstract fun sosAlertsDao() : SosAlertsDao
}