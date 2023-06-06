package com.movieapp.flashapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData
import com.movieapp.flashapp.repository.FlashLightRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashLightViewModel
@Inject constructor(
    private val flashLightRepo: FlashLightRepo,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val sharedPrefs = context?.getSharedPreferences("FlashLight", Context.MODE_PRIVATE)

    var data2: MutableLiveData<List<FlashLightData>> = MutableLiveData()
    var toast: MutableLiveData<String> = MutableLiveData()
    var progresBar: MutableLiveData<Boolean> = MutableLiveData(true)
    var errorMessage: MutableLiveData<Boolean> = MutableLiveData()


    var notFoundText: MutableLiveData<Boolean> = MutableLiveData()
    private val _searchResults = MutableLiveData<List<FlashLightData>>()
    private val searchResults: LiveData<List<FlashLightData>> get() = _searchResults

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
                    val response = flashLightRepo.getFlashFromApi()
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            flashLightRepo.addFlashLight(data)
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

    fun getYourDataFromRoom(): LiveData<List<FlashLightData>> {
        toast.postValue("VERİLER LOKALDEN GELDİ")
        return flashLightRepo.getDataRoomFlash()

    }


    fun getColorApi() {
        getColorLightDataFromApi()
    }

    private fun getColorLightDataFromApi() = viewModelScope.launch {
        progresBar.value = true
        errorMessage.value = false

        try {

            val response = flashLightRepo.getFlashFromApi()
            if (response.isSuccessful) {
                flashLightRepo.deleteList(response.body()!!)
                val data = response.body()
                if (data != null) {
                    progresBar.value = false
                    errorMessage.value = false
                    data2.postValue(data!!)
                    toast.postValue("VERİLER İNTERNETTEN GELDİ")
                }
            }
        } catch (e: java.lang.Exception) {
            println(e)
        }


    }


    fun searchFlashLight(data: String): LiveData<List<FlashLightData>> {
        viewModelScope.launch {
            val result = flashLightRepo.searchFlashLight(data)
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

    fun descRatingValue() : LiveData<List<FlashLightData>> {
        return flashLightRepo.descRatingValue()
    }
    fun ascRatingValue() : LiveData<List<FlashLightData>> {
        return flashLightRepo.ascRatingValue()
    }

    fun ascRatingCount() : LiveData<List<FlashLightData>> {
        return flashLightRepo.ascRatingCount()
    }
    fun descRatingCount() : LiveData<List<FlashLightData>> {
        return flashLightRepo.descRatingCount()
    }

}