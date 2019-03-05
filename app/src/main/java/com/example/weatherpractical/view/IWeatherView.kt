package com.example.weatherpractical.view

import com.example.weatherpractical.db.Weather
import com.example.weatherpractical.model.WeatherModel

interface IWeatherView{
    fun setWeather(model : WeatherModel)
    fun setWeather(dbModel : Weather?)
}