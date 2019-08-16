package com.tasos.kairos.data.network.response


import com.google.gson.annotations.SerializedName
import com.tasos.kairos.data.db.Entity.CurrentWeatherEntry
import com.tasos.kairos.data.db.Entity.Location

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)