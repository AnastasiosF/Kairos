package com.tasos.kairos.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tasos.kairos.data.db.Entity.CurrentWeatherEntry


@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
)
abstract class ForecastDatabase :RoomDatabase(){

    abstract fun currentWeatherDao():CurrentWeatherDao


    companion object{
        //Volatile means all threads have access to property
        @Volatile private var instance:ForecastDatabase?=null

        //Object to prevent multiple threads have access to database
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java,
                "forecast.db")
                .build()
    }
}