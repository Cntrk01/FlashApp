package com.movieapp.flashapp.service

import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    companion object{
        const val BASE_URL="https://m104e.wiremockapi.cloud/"
    }


    @GET("v1/flashlights")
    @Headers("")
    suspend fun getFlashlights(): Response<List<FlashLightData>>

    @GET("v1/colorlights")
    @Headers("")
    suspend fun getColorlights(): Response<List<ColorLightData>>

    @GET("v1/sosalerts")
    @Headers("")
    suspend fun getSosalerts(): Response<List<SosAlertsData>>
}