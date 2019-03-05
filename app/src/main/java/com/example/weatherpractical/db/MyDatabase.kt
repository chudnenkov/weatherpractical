package com.example.weatherpractical.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.weatherpractical.ioThread

@Database(entities = arrayOf(City::class, Weather::class), version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao() : WeatherDao

    companion object {
        private var instance: MyDatabase? = null
        @Synchronized
        fun get(context: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java, "MyDatabase"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            fillInDb(context.applicationContext)
                        }
                    }).build()
            }
            return instance!!
        }


        private fun fillInDb(context: Context) {
            // inserts in Room are executed on the current thread, so we insert in the background
            ioThread {
                get(context).cityDao().insert(
                    INITIAL_DATA.map { City(id = 0, name = it) })
            }
        }
    }
}
private val INITIAL_DATA = arrayListOf("Lviv")