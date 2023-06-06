package com.movieapp.flashapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData
import com.movieapp.flashapp.repository.FlashLightRepo
import com.movieapp.flashapp.repository.SosAlertsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SosAlertsViewModel @Inject constructor(private val sosAlertsRepo: SosAlertsRepo,
                                             @ApplicationContext private val context: Context

) : ViewModel() {
    val sharedPrefs = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    var data2:MutableLiveData<List<SosAlertsData>> =MutableLiveData()
    var toast:MutableLiveData<String> = MutableLiveData()
    var progresBar:MutableLiveData<Boolean> = MutableLiveData(true)
    var errorMessage:MutableLiveData<Boolean> = MutableLiveData()
    var notFoundText:MutableLiveData<Boolean> = MutableLiveData()

    private val _searchResults = MutableLiveData<List<SosAlertsData>>()
    private val searchResults: LiveData<List<SosAlertsData>> get() = _searchResults

    init {
        addRoom()

    }
    private fun addRoom(){
        val isDataAdded = sharedPrefs?.getBoolean("dataAdded", true)
       if (isDataAdded!!){
           viewModelScope.launch {
               progresBar.value=true
               errorMessage.value=false
               try {
                   val response=sosAlertsRepo.getFlashFromApi()
                   if (response.isSuccessful){
                       val data = response.body()
                       if (data != null) {
                           sosAlertsRepo.addFlashLight(data)
                           progresBar.value=false
                           errorMessage.value=false
                           val editor = sharedPrefs?.edit()
                           editor?.putBoolean("dataAdded", false)
                           editor?.apply()
                       }
                   }
               }catch (e : java.lang.Exception){
                   println(e)
               }


           }
       }else{
           getYourDataFromRoom()
       }


    }




    fun getYourDataFromRoom(): LiveData<List<SosAlertsData>> {
        toast.postValue("VERİLER LOKALDEN GELDİ")
        return sosAlertsRepo.getDataRoomFlash()

    }


    fun getColorApi(){
        getColorLightDataFromApi()
    }

    private fun getColorLightDataFromApi()=viewModelScope.launch {
        progresBar.value=true
        errorMessage.value=false
        notFoundText.value=false

        try {

            val response=sosAlertsRepo.getFlashFromApi()
            if (response.isSuccessful){
                sosAlertsRepo.deleteList(response.body()!!)
                val data=response.body()
                if (data !=null){
                    progresBar.value=false
                    errorMessage.value=false
                    data2.postValue(data!!)
                    toast.postValue("VERİLER İNTERNETTEN GELDİ")
                }
            }
        }catch (e:java.lang.Exception){
            println(e)
        }


    }


    fun searchSosAlert(data:String):LiveData<List<SosAlertsData>>{
        viewModelScope.launch{
            val result= sosAlertsRepo.searchSosAlert(data)
            _searchResults.value=result
            //Kullanıcı arama işleminde bir sonuç olmazsa hata mesajı bastırıyor ekrana
            if (result.isEmpty()){
                errorMessage.value=false
                progresBar.value=false
                notFoundText.value=true
            }else{
                errorMessage.value=false
                notFoundText.value=false
                progresBar.value=false
            }
        }
        return searchResults

    }

    fun descRatingValue() : LiveData<List<SosAlertsData>> {
        return sosAlertsRepo.descRatingValue()
    }
    fun ascRatingValue() : LiveData<List<SosAlertsData>> {
        return sosAlertsRepo.ascRatingValue()
    }

    fun ascRatingCount() : LiveData<List<SosAlertsData>> {
        return sosAlertsRepo.ascRatingCount()
    }
    fun descRatingCount() : LiveData<List<SosAlertsData>> {
        return sosAlertsRepo.descRatingCount()
    }
}