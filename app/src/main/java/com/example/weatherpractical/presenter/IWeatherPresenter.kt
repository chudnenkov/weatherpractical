package com.example.weatherpractical.presenter

import com.example.weatherpractical.db.Weather

interface IWeatherPresenter {

    fun getCityWeather()
    fun getDBSavedCityWeather(city : String)
}