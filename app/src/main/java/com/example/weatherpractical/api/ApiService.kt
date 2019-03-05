package com.example.weatherpractical.api

import com.example.weatherpractical.model.WeatherModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?")
    fun getWeather(@Query("q") q: String, @Query("APPID") APPID: String): Observable<WeatherModel>

    companion object {
        fun create(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request().newBuilder().header("Accept", "application/json").build()
                    //request.newBuilder().header("Accept", "application/json").build()
                    chain.proceed(request)
                }
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                //.client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://samples.openweathermap.org/data/2.5/")
                .build();
            return retrofit.create(ApiService::class.java)
        }
    }
}