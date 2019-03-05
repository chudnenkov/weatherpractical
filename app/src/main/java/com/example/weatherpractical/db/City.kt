package com.example.weatherpractical.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class City(@PrimaryKey(autoGenerate = true) val id: Int, val name: String)