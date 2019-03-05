package com.example.weatherpractical.presenter

import com.example.weatherpractical.db.City
import com.example.weatherpractical.db.Weather

interface IMainPresenter{
    fun getCityList()
    fun addCityToDB(city : City)
    fun saveWeatherToDB(cityName : String, dt : Long, temp: String, pressure : String, humidity : String)
}