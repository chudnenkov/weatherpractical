package com.example.weatherpractical.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey



@Entity(foreignKeys = [ForeignKey(
    entity = City::class,
    parentColumns = ["id"], childColumns = ["city"]
)])
data class Weather(@PrimaryKey(autoGenerate = true) val id: Int?, val city: Int?, val dt : Long,
                   val temp: String, val pressure : String, val humidity : String)
