package com.example.pickuppal

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pickuppal.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.tasks.Task

class MapFragment : Fragment(), OnMapReadyCallback
{
    private var _binding: FragmentMapBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var currentLocation: Location

    private lateinit var currentLocationDeterminer: CurrentLocationDeterminer

    private var mapPins = mutableListOf<LatLng>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding =
            FragmentMapBinding.inflate(layoutInflater, container, false)

        //val view = inflater.inflate(R.layout.fragment_map, container, false)

        var task = determineCurrentLocation()
        task.addOnSuccessListener{ location ->
            if(location != null)
            {
                currentLocation = location

                val mapFragment = childFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
            else { // if a current location cannot be determined, default to Boston University
                currentLocation = Location("mockedLocationProvider").apply {
                    latitude = 42.350876
                    longitude = -71.106918
                }

                val mapFragment = childFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }


        binding.addItemButton.setOnClickListener {
            val address = "575 Commonwealth Ave, Boston, MA"

        }

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker at your current location and move the camera
        val startingLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
        // will not actually add marker at staring location
        // just initial practice in adding markers
        for(pin in mapPins) {
            googleMap.addMarker(
                MarkerOptions().position(pin).title("New Pin")
            )
        }
        googleMap.addMarker(
            MarkerOptions().position(startingLocation).title("New Pin"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingLocation, 15f))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentLocationDeterminer = context as CurrentLocationDeterminer
    }

    private fun determineCurrentLocation(): Task<Location>
    {
        return currentLocationDeterminer.determineCurrentLocation()
    }

}