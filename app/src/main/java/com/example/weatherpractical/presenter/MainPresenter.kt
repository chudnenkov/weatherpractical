package com.example.weatherpractical.presenter

import android.content.Context

import android.util.Log
import com.example.weatherpractical.db.City
import com.example.weatherpractical.db.MyDatabase
import com.example.weatherpractical.db.Weather
import com.example.weatherpractical.ioThread
import com.example.weatherpractical.view.IMainView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(val mContext: Context) : IMainPresenter {
    private lateinit var db: MyDatabase

    override fun getCityList() {
        Observable.fromCallable({

            db = MyDatabase.get(context = mContext)
            db?.cityDao()?.getCities()
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result != null && mContext is IMainView)
                        mContext.showCityList(result as MutableList<City>)
                },
                { error ->
                    Log.e("TAG", "{$error.message}")
                })
    }

    override fun addCityToDB(city: City) {
        db = MyDatabase.get(context = mContext)
        ioThread {
            if (db?.cityDao()?.getCityId(city.name) == 0) {
                db?.cityDao()?.insert(city)
            }
        }
    }

    override fun saveWeatherToDB(cityName: String, dt: Long, temp: String, pressure: String, humidity: String) {
        db = MyDatabase.get(context = mContext)
        ioThread {
            db?.weatherDao()?.insert(Weather(0, db?.cityDao()?.getCityId(cityName), dt, temp, pressure, humidity))
        }

    }

}
