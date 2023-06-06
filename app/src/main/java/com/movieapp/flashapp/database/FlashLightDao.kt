package com.movieapp.flashapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData

@Dao
interface FlashLightDao {

    @Query("SELECT COUNT(*) FROM flashlights")
    suspend fun getColorLightCount(): Int


    @Query("SELECT * FROM flashlights")
    fun getFlashLight(): LiveData<List<FlashLightData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashLight(flashlights: List<FlashLightData>)


    @Delete
    suspend fun deleteFlashLight(delete: List<FlashLightData>)

    @Query("SELECT * FROM flashlights WHERE category LIKE :searchQuery OR developerName LIKE :searchQuery OR name LIKE :searchQuery OR developerName LIKE :searchQuery" )
    suspend fun searchInTable(searchQuery: String): List<FlashLightData>


    @Query("SELECT * FROM flashlights ORDER BY ratingValue DESC")
    fun descRatingValue(): LiveData<List<FlashLightData>>

    @Query("SELECT * FROM flashlights ORDER BY ratingValue ASC")
    fun ascRatingValue(): LiveData<List<FlashLightData>>

    @Query("SELECT * FROM flashlights ORDER BY ratingCount ASC")
    fun ascRatingCount(): LiveData<List<FlashLightData>>

    @Query("SELECT * FROM flashlights ORDER BY ratingCount DESC")
    fun descRatingCount(): LiveData<List<FlashLightData>>
}