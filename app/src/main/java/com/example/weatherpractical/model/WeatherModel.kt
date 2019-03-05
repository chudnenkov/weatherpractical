package com.example.weatherpractical.model

data class WeatherModel(val main : Main, val dt : Long){
    data class Main(val temp : String, val pressure : String, val humidity : String)
}