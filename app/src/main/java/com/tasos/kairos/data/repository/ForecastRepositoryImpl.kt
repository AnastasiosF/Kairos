package com.tasos.kairos.data.repository

import androidx.lifecycle.LiveData
import com.tasos.kairos.data.db.CurrentWeatherDao
import com.tasos.kairos.data.db.Entity.WeatherLocation
import com.tasos.kairos.data.db.WeatherLocationDao
import com.tasos.kairos.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.tasos.kairos.data.network.WeatherNetworkDataSource
import com.tasos.kairos.data.network.response.CurrentWeatherResponse
import com.tasos.kairos.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {



    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever{ newCurrentWeather->
                persistFetchedCurrentWeather(newCurrentWeather)

            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData< out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<out WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather:CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation  = weatherLocationDao.getLocation().value
        if(lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)){
            fetchCurrentWeather()
        }

    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(), Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}