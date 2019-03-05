package com.example.weatherpractical.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE city  == :cityId ORDER BY dt desc limit 1")
    fun getWeather(cityId: Int): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather)
}