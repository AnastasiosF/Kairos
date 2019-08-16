package com.tasos.kairos.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasos.kairos.data.db.Entity.CURRENT_WEATHER_ID
import com.tasos.kairos.data.db.Entity.CurrentWeatherEntry
import com.tasos.kairos.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.tasos.kairos.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.tasos.kairos.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric():LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial():LiveData<ImperialCurrentWeatherEntry>

}