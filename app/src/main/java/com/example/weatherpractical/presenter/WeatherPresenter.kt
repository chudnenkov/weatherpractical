package com.example.weatherpractical.presenter

import android.content.Context
import android.support.annotation.MainThread
import android.util.Log
import com.example.weatherpractical.api.ApiService
import com.example.weatherpractical.db.City
import com.example.weatherpractical.db.MyDatabase
import com.example.weatherpractical.db.Weather
import com.example.weatherpractical.ioThread
import com.example.weatherpractical.model.WeatherModel
import com.example.weatherpractical.view.IWeatherView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherPresenter(val mContext: Context, val city: String) : IWeatherPresenter {
    private lateinit var db: MyDatabase
    private var weather: Weather? = null
    //private var cityId : Int? =null

    override fun getCityWeather() {
        ApiService.create().getWeather(city + ",ua","b6907d289e10d714a6e88b30761fae22")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.e("tester", "all ok")
                if (mContext is IWeatherView)
                    mContext.setWeather(result)
            }, { error -> Log.e("tester", "this is error") })
    }

    override fun getDBSavedCityWeather(city : String){
        db = MyDatabase.get(context = mContext)

        ioThread {
            weather = db.weatherDao().getWeather(db?.cityDao()?.getCityId(city))
            if (mContext is IWeatherView)
                mContext.setWeather(weather)
        }
    }
}