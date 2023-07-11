package com.devyash.mapprac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFrgament) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(gooleMap: GoogleMap) {
        mMap = gooleMap

        val noida = LatLng(28.5355, 77.3910)
        mMap.addMarker(MarkerOptions().position(noida).title("Marker in Noida"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(noida))
    }
}