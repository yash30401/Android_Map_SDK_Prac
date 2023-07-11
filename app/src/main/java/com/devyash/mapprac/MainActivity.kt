package com.devyash.mapprac

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devyash.mapprac.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Places.initialize(applicationContext, resources.getString(R.string.google_api_key))
        placesClient = Places.createClient(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        binding.btnMapView.setOnClickListener {
            val latitude = binding.etLatitude.text.toString()
            val longitude = binding.etLongitude.text.toString()

            Intent(this, MapViewActivity::class.java).also {
                it.putExtra("Latitude", latitude)
                it.putExtra("Longitude", longitude)
                startActivity(it)
            }
        }

        binding.btnGetCurrentLocation.setOnClickListener {
            getLastKnowLocation()
        }
    }

    private fun getLastKnowLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("PERMISSION", "Permission Granted")

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener { location ->
                if (location != null) {
                    try {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                                ?.toMutableList()
                        addresses?.let {
                            binding.tvLatitude.text = "Latitude:- ${it.get(0).latitude}"
                            binding.tvLongitude.text = "Longitude:- ${it.get(0).longitude}"
                            binding.tvAddress.text = "Address:- ${it.get(0).getAddressLine(0)}"
                            binding.tvCity.text = "City:- ${it.get(0).locality}"
                            binding.tvCountry.text = "Country:- ${it.get(0).countryName}"
                            Log.d("CITY","City:- ${it.get(0).locality}")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                12
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            12 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    getLastKnowLocation()
                }
            }

            else -> {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {

    }
}