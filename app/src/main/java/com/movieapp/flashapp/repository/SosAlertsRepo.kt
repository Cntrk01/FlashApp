package com.movieapp.flashapp.repository

import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData
import com.movieapp.flashapp.database.FlashLightDao
import com.movieapp.flashapp.database.SosAlertsDao
import com.movieapp.flashapp.service.ApiService
import javax.inject.Inject

class SosAlertsRepo @Inject constructor(private val apiService: ApiService, private val sosAlertsDao: SosAlertsDao) {
    suspend fun getFlashFromApi() = apiService.getSosalerts()

    suspend fun addFlashLight(list: List<SosAlertsData>)=sosAlertsDao.insertSosAlerts(list)

    fun getDataRoomFlash() = sosAlertsDao.getSosAlerts()

    suspend fun deleteList(deleteList: List<SosAlertsData>)=sosAlertsDao.deleteSosAlerts(deleteList)

    suspend fun searchSosAlert(data: String)=sosAlertsDao.searchInTable(data)

    suspend fun sosAlertCounts()=sosAlertsDao.getSosAlertsCount()

    fun descRatingValue()=sosAlertsDao.descRatingValue()

    fun  ascRatingValue()=sosAlertsDao.ascRatingValue()

    fun ascRatingCount() = sosAlertsDao.ascRatingCount()

    fun descRatingCount()=sosAlertsDao.descRatingCount()
}