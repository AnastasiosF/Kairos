package com.tasos.kairos

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tasos.kairos.data.db.ForecastDatabase
import com.tasos.kairos.data.network.*
import com.tasos.kairos.data.provider.LocationProvider
import com.tasos.kairos.data.provider.LocationProviderImpl
import com.tasos.kairos.data.provider.UnitProvider
import com.tasos.kairos.data.provider.UnitProviderImpl
import com.tasos.kairos.data.repository.ForecastRepository
import com.tasos.kairos.data.repository.ForecastRepositoryImpl
import com.tasos.kairos.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton {
            ForecastDatabase(instance())
        }

        bind() from singleton {
            instance<ForecastDatabase>().currentWeatherDao()
        }
        bind() from singleton {
            instance<ForecastDatabase>().weatherLocationDao()
        }


        bind<ConnectivityInterceptor>() with singleton{
            ConnectivityInterceptorImpl(instance())
        }

        bind() from singleton{
            ApixuWeatherApiService(instance())
        }
        bind<WeatherNetworkDataSource>() with singleton{
            WeatherNetworkDataSourceImpl(instance())
        }
        bind<LocationProvider>() with singleton {
            LocationProviderImpl()
        }

        bind<ForecastRepository>() with singleton{
            ForecastRepositoryImpl(instance(),instance(),instance(),instance())
        }
        bind<UnitProvider>() with singleton{
            UnitProviderImpl(instance())
        }

        bind() from provider{
            CurrentWeatherViewModelFactory(instance(),instance())
        }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        //Set defaultvalues for preferences
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }
}