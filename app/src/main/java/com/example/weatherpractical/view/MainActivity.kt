package com.example.weatherpractical

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.weatherpractical.adapter.CityListAdapter
import com.example.weatherpractical.db.City
import com.example.weatherpractical.db.Weather
import com.example.weatherpractical.presenter.MainPresenter
import com.example.weatherpractical.utils.NetworkUtils
import com.example.weatherpractical.view.IMainView
import com.example.weatherpractical.view.MapsActivity
import com.example.weatherpractical.view.WeatherActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), IMainView {
    private lateinit var mainPresenter: MainPresenter
    private var listCities: MutableList<City> = mutableListOf<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mainPresenter = MainPresenter(this)
        mainPresenter.getCityList()

        fab.setOnClickListener { view ->
            addCity()
        }

    }

    override fun showCityList(cities: MutableList<City>) {
        if (cities.isEmpty())
            listCities.add(City(0, "Lviv"))
        listCities.addAll(cities)
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        if (cities != null) {
            val adapter = CityListAdapter(listCities, { adapterPosition ->
                showWeather(adapterPosition)
            })
            mainRecyclerView.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (!listCities.contains(City(0, data!!.getStringExtra("city"))))
                listCities.add(City(0, data!!.getStringExtra("city")))
            mainPresenter.addCityToDB(City(0, data!!.getStringExtra("city")))
        }
        if (requestCode == 1 && NetworkUtils.isNetworkConnected(this) && data != null) {
            mainPresenter.saveWeatherToDB(
                data!!.getStringExtra("city"),
                data!!.getLongExtra("dt", 0), data!!.getStringExtra("temp"),
                data!!.getStringExtra("pressure"), data!!.getStringExtra("humidity")
            )
        }
        mainRecyclerView.adapter!!.notifyDataSetChanged()
    }

    fun showWeather(pos: Int) {
        intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra("city", listCities[pos].name)
        startActivityForResult(intent, 1)
    }

    fun addCity() {
        intent = Intent(this, MapsActivity::class.java)
        startActivityForResult(intent, 0)
    }


}
