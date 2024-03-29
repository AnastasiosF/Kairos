package com.tasos.kairos.data.network

import androidx.lifecycle.LiveData
import com.tasos.kairos.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>



    suspend fun fetchCurrentWeather(location:String, languageCode:String)

}