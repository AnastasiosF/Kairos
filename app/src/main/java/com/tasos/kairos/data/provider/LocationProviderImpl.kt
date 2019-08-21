package com.tasos.kairos.data.provider

import android.content.Context
import com.tasos.kairos.data.db.Entity.WeatherLocation

class LocationProviderImpl (context: Context): PreferenceProvider(context),LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPreferredLocationString(): String {
        return "Athens"
    }
}