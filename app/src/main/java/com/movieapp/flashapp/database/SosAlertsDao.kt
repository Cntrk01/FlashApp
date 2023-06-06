package com.movieapp.flashapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData

@Dao
interface SosAlertsDao {


    @Query("SELECT COUNT(*) FROM sosalerts")
    suspend fun getSosAlertsCount(): Int


    @Query("SELECT * FROM sosalerts")
    fun getSosAlerts(): LiveData<List<SosAlertsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSosAlerts(flashlights: List<SosAlertsData>)


    @Delete
    suspend fun deleteSosAlerts(delete: List<SosAlertsData>)

    @Query("SELECT * FROM sosalerts WHERE category LIKE :searchQuery OR developerName LIKE :searchQuery OR name LIKE :searchQuery OR developerName LIKE :searchQuery" )
    suspend fun searchInTable(searchQuery: String): List<SosAlertsData>

    @Query("SELECT * FROM sosalerts ORDER BY ratingValue DESC")
    fun descRatingValue(): LiveData<List<SosAlertsData>>

    @Query("SELECT * FROM sosalerts ORDER BY ratingValue ASC")
    fun ascRatingValue(): LiveData<List<SosAlertsData>>

    @Query("SELECT * FROM sosalerts ORDER BY ratingCount ASC")
    fun ascRatingCount(): LiveData<List<SosAlertsData>>

    @Query("SELECT * FROM sosalerts ORDER BY ratingCount DESC")
    fun descRatingCount(): LiveData<List<SosAlertsData>>
}