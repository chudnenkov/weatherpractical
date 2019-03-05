package com.example.weatherpractical.view

import com.example.weatherpractical.db.City

interface IMainView {
    fun showCityList(cities : MutableList<City>)
}