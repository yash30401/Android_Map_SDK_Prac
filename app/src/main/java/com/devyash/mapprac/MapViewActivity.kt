package com.devyash.mapprac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devyash.mapprac.databinding.ActivityMapViewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private var _binding: ActivityMapViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap

    private var latitude:String? = ""
    private var longitude:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFrgament) as SupportMapFragment
        mapFragment.getMapAsync(this)

        latitude = intent?.getStringExtra("Latitude")
        longitude = intent?.getStringExtra("Longitude")

        Log.d("MAPMARKER","Latitude(onCreate):- ${latitude.toString()}")
        Log.d("MAPMARKER","Latitude(onCreate):- ${longitude.toString()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(gooleMap: GoogleMap) {
        mMap = gooleMap

        if(latitude != "" && longitude !=""){
            Log.d("MAPMARKER","Latitude(onMapReady):- ${latitude.toString()}")
            Log.d("MAPMARKER","Latitude(onMapReady):- ${longitude.toString()}")

            val location = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
            mMap.addMarker(MarkerOptions().position(location).title("Marker At Your Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        }else{
            val noida = LatLng(28.5355, 77.3910)
            mMap.addMarker(MarkerOptions().position(noida).title("Marker In Noida"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(noida))
        }

    }
}