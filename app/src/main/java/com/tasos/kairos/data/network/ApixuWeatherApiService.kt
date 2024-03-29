package com.tasos.kairos.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tasos.kairos.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "04a5eb1001e14836abf105202191608"

//http://api.apixu.com/v1/current.json?key=04a5eb1001e14836abf105202191608&q=Paris&lang=en
interface ApixuWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location:String,
        @Query("lang") languageCode:String = "en"
    ): Deferred<CurrentWeatherResponse>
    //Karfwnw to api key sto url
    companion object{
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApixuWeatherApiService {
            val requestInterceptor = Interceptor{chain->
                val url =chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)

            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor) // Elegxei an uparxei sundesh sto internet
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)


        }
    }
}