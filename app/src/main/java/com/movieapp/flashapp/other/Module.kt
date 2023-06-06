package com.movieapp.flashapp.other

import android.content.Context
import androidx.room.Room
import com.movieapp.flashapp.database.FlashLightDao
import com.movieapp.flashapp.database.FlashlightDatabase
import com.movieapp.flashapp.database.ColorLightDao
import com.movieapp.flashapp.database.SosAlertsDao
import com.movieapp.flashapp.service.ApiService
import com.movieapp.flashapp.viewmodel.SosAlertsViewModel
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit) : ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideFlashlightDatabase(@ApplicationContext appContext: Context): FlashlightDatabase {
        return Room.databaseBuilder(appContext, FlashlightDatabase::class.java, "app-database1")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideColorDao(database: FlashlightDatabase): ColorLightDao {
        return database.colorLightDao()
    }
    @Singleton
    @Provides
    fun provideFlashlightDao(database: FlashlightDatabase): FlashLightDao {
        return database.flashLightDao()
    }

    @Singleton
    @Provides
    fun provideSosAlertsDao(database: FlashlightDatabase): SosAlertsDao {
        return database.sosAlertsDao()
    }



}