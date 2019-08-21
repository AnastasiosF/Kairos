package com.tasos.kairos.data.provider

import com.tasos.kairos.data.db.Entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation):Boolean
    suspend fun getPreferredLocationString():String
}