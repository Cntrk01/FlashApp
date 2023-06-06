package com.movieapp.flashapp.repository

import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.database.ColorLightDao
import com.movieapp.flashapp.service.ApiService
import javax.inject.Inject

class ColorLightRepo @Inject constructor(private val apiService: ApiService,private val colorLightDao: ColorLightDao) {

    suspend fun getColorLight() = apiService.getColorlights()

    suspend fun addColorLight(list: List<ColorLightData>)=colorLightDao.insertColorLight(list)

    fun getDataRoomLight() = colorLightDao.getColorLights()

    //suspend fun counter()=lightDao.getColorLightCount()

    suspend fun getDataFromApi()=apiService.getColorlights()

    suspend fun deleteList(deleteList: List<ColorLightData>)=colorLightDao.deleteFlashLight(deleteList)

    suspend fun searchColorLight(string: String) = colorLightDao.searchInTable(string)

    fun descRatingValue()=colorLightDao.descRatingValue()

    fun  ascRatingValue()=colorLightDao.ascRatingValue()

    fun ascRatingCount() = colorLightDao.ascRatingCount()

    fun descRatingCount()=colorLightDao.descRatingCount()
}