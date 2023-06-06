package com.movieapp.flashapp.viewmodel

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.repository.ColorLightRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorLightViewModel @Inject constructor(
    private val colorLightRepo: ColorLightRepo,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val sharedPrefs = context?.getSharedPreferences("ColorLight", Context.MODE_PRIVATE)
    var data2: MutableLiveData<List<ColorLightData>> = MutableLiveData()
    var toast: MutableLiveData<String> = MutableLiveData()
    var progresBar: MutableLiveData<Boolean> = MutableLiveData(true)
    var errorMessage: MutableLiveData<Boolean> = MutableLiveData()

    var descRating: LiveData<List<ColorLightData>> =MutableLiveData()

    var notFoundText: MutableLiveData<Boolean> = MutableLiveData()
    private val _searchResults = MutableLiveData<List<ColorLightData>>()
    private val searchResults: LiveData<List<ColorLightData>> get() = _searchResults

    init {
        addRoom()

    }

    private fun addRoom() {
        val isDataAdded = sharedPrefs?.getBoolean("dataAdded", true)
        if (isDataAdded!!) {
            viewModelScope.launch {
                progresBar.value = true
                errorMessage.value = false
                try {
                    val response = colorLightRepo.getColorLight()
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            colorLightRepo.addColorLight(data)
                            progresBar.value = false
                            errorMessage.value = false
                            notFoundText.value=false
                            val editor = sharedPrefs?.edit()
                            editor?.putBoolean("dataAdded", false)
                            editor?.apply()
                        }
                    }
                } catch (e: java.lang.Exception) {
                    println(e)
                }
            }

        } else {
            getYourDataFromRoom()
        }

    }

    fun getYourDataFromRoom(): LiveData<List<ColorLightData>> {
        toast.postValue("VERİLER LOKALDEN GELDİ")
        return colorLightRepo.getDataRoomLight()

    }


    fun getColorApi() {
        getColorLightDataFromApi()
    }

    private fun getColorLightDataFromApi() = viewModelScope.launch {
        progresBar.value = true
        errorMessage.value = false
        notFoundText.value=false

        try {

            val response = colorLightRepo.getDataFromApi()
            if (response.isSuccessful) {
                colorLightRepo.deleteList(response.body()!!)
                val data = response.body()
                if (data != null) {
                    progresBar.value = false
                    errorMessage.value = false
                    notFoundText.value=false
                    data2.postValue(data!!)
                    if (progresBar.value == false) {
                        toast.postValue("VERİLER İNTERNETTEN GELDİ")
                    }

                }
            }
        } catch (e: java.lang.Exception) {
            println(e)
        }


    }

    fun searchFlashLight(data: String): LiveData<List<ColorLightData>> {
        viewModelScope.launch {
            val result = colorLightRepo.searchColorLight(data)
            _searchResults.value = result
            //Kullanıcı arama işleminde bir sonuç olmazsa hata mesajı bastırıyor ekrana
            if (result.isEmpty()) {
                errorMessage.value = false
                progresBar.value = false
                notFoundText.value = true
            } else {
                errorMessage.value = false
                notFoundText.value = false
                progresBar.value = false
            }
        }
        return searchResults

    }

    fun descRatingValue() : LiveData<List<ColorLightData>> {
        return colorLightRepo.descRatingValue()
    }
    fun ascRatingValue() : LiveData<List<ColorLightData>> {
        return colorLightRepo.ascRatingValue()
    }

    fun ascRatingCount() : LiveData<List<ColorLightData>> {
        return colorLightRepo.ascRatingCount()
    }
    fun descRatingCount() : LiveData<List<ColorLightData>> {
        return colorLightRepo.descRatingCount()
    }

}