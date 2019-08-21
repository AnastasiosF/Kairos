package com.tasos.kairos.data.repository

import androidx.lifecycle.LiveData
import com.tasos.kairos.data.db.Entity.WeatherLocation
import com.tasos.kairos.data.db.WeatherLocationDao
import com.tasos.kairos.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean):LiveData< out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation():LiveData< out WeatherLocation>
}