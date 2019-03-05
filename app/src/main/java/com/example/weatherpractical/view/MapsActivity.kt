package com.example.weatherpractical.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.weatherpractical.MainActivity
import com.example.weatherpractical.R
import com.example.weatherpractical.db.City
import com.example.weatherpractical.db.MyDatabase
import com.example.weatherpractical.utils.NetworkUtils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var marker: Marker
    private var city: String = "Kyiv"
    private var countryCode: String? = "UA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getWhether.setOnClickListener {
            val result = Intent()
            result.putExtra("city", city)
            result.putExtra("countryCode", countryCode)
            setResult(Activity.RESULT_OK, result)
            finish()
        }
        checkPermission()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Kyiv, Ukraine.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Kyiv and move the camera
        val Kyiv = LatLng(50.30, 30.31)
        marker = mMap.addMarker(MarkerOptions().position(Kyiv).title("Marker in Kyiv"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Kyiv))

        geocoding(Kyiv)
        //mMap.isMyLocationEnabled = true

        mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                if (marker != null) {
                    marker.remove()
                }
                marker = mMap.addMarker(MarkerOptions().position(latLng).title("city"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                geocoding(latLng)
            }
        })
    }

    fun geocoding(latLng: LatLng){
        val geocoder = Geocoder(this@MapsActivity, Locale.ENGLISH)
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (addresses != null && addresses.size > 0 && addresses[0].locality != null) {
            val address = addresses[0]
            city = address.locality
            countryCode = address.countryCode
        }
    }

    private fun checkPermission() {
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!hasPermissions(permissions)) {
            var str = arrayOfNulls<String>(permissions.size)
            str = permissions.toTypedArray<String?>()
            ActivityCompat.requestPermissions(this, str, 10)
        }
    }

    fun hasPermissions(permissions: ArrayList<String>?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

}
