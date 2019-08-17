package com.tasos.kairos.ui.weather.current

import androidx.lifecycle.ViewModel
import com.tasos.kairos.data.repository.ForecastRepository
import com.tasos.kairos.internal.UnitSystem
import com.tasos.kairos.internal.lazyDeferred

class CurrentWeatherViewModel (private val forecastRepository:ForecastRepository)
    : ViewModel() {

    private val unitSystem = UnitSystem.METRIC //Get from settings

    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
