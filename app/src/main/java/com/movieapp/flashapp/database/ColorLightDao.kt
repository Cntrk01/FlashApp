package com.movieapp.flashapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData

@Dao
interface ColorLightDao {
    @Query("SELECT * FROM flashlights1")
    fun getColorLights(): LiveData<List<ColorLightData>>

    @Query("SELECT COUNT(*) FROM flashlights1")
    suspend fun getColorLightCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColorLight(flashlights: List<ColorLightData>)


    @Query("SELECT * FROM flashlights1")
    fun getFlashLight(): LiveData<List<ColorLightData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashLight(flashlights: List<ColorLightData>)


    @Delete
    suspend fun deleteFlashLight(delete: List<ColorLightData>)

    @Query("SELECT * FROM flashlights1 WHERE category LIKE :searchQuery OR developerName LIKE :searchQuery OR name LIKE :searchQuery OR developerName LIKE :searchQuery" )
    suspend fun searchInTable(searchQuery: String): List<ColorLightData>


    @Query("SELECT * FROM flashlights1 ORDER BY ratingValue DESC")
    fun descRatingValue(): LiveData<List<ColorLightData>>

    @Query("SELECT * FROM flashlights1 ORDER BY ratingValue ASC")
    fun ascRatingValue(): LiveData<List<ColorLightData>>

    @Query("SELECT * FROM flashlights1 ORDER BY ratingCount ASC")
    fun ascRatingCount(): LiveData<List<ColorLightData>>

    @Query("SELECT * FROM flashlights1 ORDER BY ratingCount DESC")
    fun descRatingCount(): LiveData<List<ColorLightData>>
}