package com.example.weatherpractical.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.weatherpractical.R
import com.example.weatherpractical.db.Weather
import com.example.weatherpractical.model.WeatherModel
import com.example.weatherpractical.presenter.WeatherPresenter
import com.example.weatherpractical.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.*

class WeatherActivity : AppCompatActivity(), IWeatherView {

    private lateinit var weatherPresenter: WeatherPresenter
    private lateinit var city: String
    private var dt: Long? = null
    private lateinit var temp: String
    private lateinit var pressure: String
    private lateinit var humidity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        city = intent.getStringExtra("city")
        (this as AppCompatActivity).supportActionBar!!.setTitle(intent.getStringExtra("city"))
        weatherPresenter = WeatherPresenter(this, intent.getStringExtra("city"))
        if (NetworkUtils.isNetworkConnected(this)) {
            weatherPresenter.getCityWeather()
        } else {
            weatherPresenter.getDBSavedCityWeather(city)
        }
    }

    override fun setWeather(model: WeatherModel) {
        dt = model?.dt
        temp = model.main.temp
        pressure = model.main.pressure
        humidity = model.main.humidity
        itemDate.setText(Date(model.dt * 1000).toString())
        itemTemperature.setText("Temperature: " + model.main.temp + "F")
        itemPressure.setText("Pressure: " + model.main.pressure)
        itemHumidity.setText("Humidity: " + model.main.humidity)
        progressBar.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    override fun setWeather(dbModel: Weather?) {
        if (dbModel != null)
            itemDate.setText(Date(dbModel?.dt * 1000).toString())
        itemTemperature.setText("Temperature: " + dbModel?.temp + "F")
        itemPressure.setText("Pressure: " + dbModel?.pressure)
        itemHumidity.setText("Humidity: " + dbModel?.humidity)
        progressBar.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (NetworkUtils.isNetworkConnected(this)) {
            val result = Intent()
            result.putExtra("city", city)
            result.putExtra("dt", dt)
            result.putExtra("temp", temp)
            result.putExtra("pressure", pressure)
            result.putExtra("humidity", humidity)
            setResult(Activity.RESULT_OK, result)
        }
        super.onBackPressed()
    }
}