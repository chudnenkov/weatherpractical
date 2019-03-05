package com.example.weatherpractical.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM city ORDER BY name COLLATE NOCASE ASC")
    fun getCities(): List<City>

    @Insert
    fun insert(cities: List<City>)

    @Insert
    fun insert(city: City)

    @Delete
    fun delete(city: City)

    @Query("SELECT id FROM city WHERE name = :name LIMIT 1")
    fun getCityId(name : String) : Int


}