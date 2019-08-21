package com.tasos.kairos.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasos.kairos.data.db.Entity.WEATHER_LOCATION_ID
import com.tasos.kairos.data.db.Entity.WeatherLocation


@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation():LiveData<WeatherLocation>
}