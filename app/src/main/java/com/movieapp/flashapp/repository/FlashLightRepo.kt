package com.movieapp.flashapp.repository

import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.database.ColorLightDao
import com.movieapp.flashapp.database.FlashLightDao
import com.movieapp.flashapp.service.ApiService
import javax.inject.Inject

class FlashLightRepo @Inject constructor(private val apiService: ApiService, private val flashLightDao: FlashLightDao) {
    suspend fun getFlashFromApi() = apiService.getFlashlights()

    suspend fun addFlashLight(list: List<FlashLightData>)=flashLightDao.insertFlashLight(list)

    fun getDataRoomFlash() = flashLightDao.getFlashLight()

    suspend fun deleteList(deleteList: List<FlashLightData>)=flashLightDao.deleteFlashLight(deleteList)

    suspend fun searchFlashLight(string: String) = flashLightDao.searchInTable(string)

    fun descRatingValue()=flashLightDao.descRatingValue()

    fun  ascRatingValue()=flashLightDao.ascRatingValue()

    fun ascRatingCount() = flashLightDao.ascRatingCount()

    fun descRatingCount()=flashLightDao.descRatingCount()
}