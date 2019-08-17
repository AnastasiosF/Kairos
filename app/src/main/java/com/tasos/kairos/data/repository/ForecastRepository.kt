package com.tasos.kairos.data.repository

import androidx.lifecycle.LiveData
import com.tasos.kairos.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean):LiveData< out UnitSpecificCurrentWeatherEntry>
}