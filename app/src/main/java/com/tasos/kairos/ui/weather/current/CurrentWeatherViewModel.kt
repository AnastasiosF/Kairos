package com.tasos.kairos.ui.weather.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.tasos.kairos.data.provider.UnitProvider
import com.tasos.kairos.data.repository.ForecastRepository
import com.tasos.kairos.internal.UnitSystem
import com.tasos.kairos.internal.lazyDeferred

class CurrentWeatherViewModel (private val forecastRepository:ForecastRepository,
                               private val unitProvider: UnitProvider)
    : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)

    }
    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
