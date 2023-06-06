package com.movieapp.flashapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashlights1")
data class ColorLightData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "developerAddress")
    val developerAddress: String?,
    @ColumnInfo(name = "developerEmail")
    val developerEmail: String,
    @ColumnInfo(name = "developerName")
    val developerName: String,
    @ColumnInfo(name = "downloads")
    val downloads: String,
    @ColumnInfo(name = "iconUrl")
    val iconUrl: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "packageName")
    val packageName: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "publishDate")
    val publishDate: PublishDate,
    @ColumnInfo(name = "ratingCount")
    val ratingCount: Int,
    @ColumnInfo(name = "ratingValue")
    val ratingValue: Double,
    @ColumnInfo(name = "version")
    val version: String

)