package com.tasos.kairos.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tasos.kairos.data.network.response.CurrentWeatherResponse
import com.tasos.kairos.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService:ApixuWeatherApiService
) : WeatherNetworkDataSource {


    private val _downCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {

        try{
            val fetchCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(location,languageCode)
                .await()
            _downCurrentWeather.postValue(fetchCurrentWeather)
        }catch(e:NoConnectivityException){
            Log.e("Connectivity","No internet connection.",e)
        }

    }
}