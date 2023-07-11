package com.devyash.mapprac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devyash.mapprac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMapView.setOnClickListener {
            val latitude = binding.etLatitude.text.toString()
            val longitude = binding.etLongitude.text.toString()

            Intent(this, MapViewActivity::class.java).also {
                it.putExtra("Latitude",latitude)
                it.putExtra("Longitude",longitude)
                startActivity(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}