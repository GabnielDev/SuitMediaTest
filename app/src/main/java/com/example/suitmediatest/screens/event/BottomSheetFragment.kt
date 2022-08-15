package com.example.suitmediatest.screens.event

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.example.suitmediatest.R
import com.example.suitmediatest.R.id.map
import com.example.suitmediatest.databinding.FragmentBottomSheetBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class BottomSheetFragment(
    private val tag: Int,
    private val name: String? = null,
    private val image: String? = null
) : DialogFragment(), OnMapReadyCallback {

    var currentMarker: Marker? = null
    private lateinit var mGoogleMap: GoogleMap
    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    var currentLocation: Location? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        setupLayout()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)
        fetchLocation()
        setupToolbar()
        return view
    }

    private fun setupToolbar() {
        binding.toolbar.run {
            txtTitle.text = "Event"
            btnBack.setOnClickListener {
            }
        }
    }

    private fun setupLayout() {
        binding.txtName.text = name
        binding.imgPhoto.setImageURI(image?.toUri())


    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }


    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
            return
        }
        val task = fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener { location ->
            if (location != null) {
                this.currentLocation = location
                val mapFragment =
                    childFragmentManager.findFragmentById(map) as SupportMapFragment?
                mapFragment?.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1000 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val latlong = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
        drawMarker(latlong)

        mGoogleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if (currentMarker != null)
                    currentMarker?.remove()

                val newLatLng = LatLng(p0.position.latitude, p0.position.longitude)
                drawMarker(newLatLng)
            }

            override fun onMarkerDragStart(p0: Marker) {

            }

        })

    }

    private fun drawMarker(latLng: LatLng) {
        val markerOption = MarkerOptions().position(latLng).title("Disini")
            .snippet(getAddress(latLng.latitude, latLng.longitude)).draggable(true)

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        currentMarker = mGoogleMap.addMarker(markerOption)
        currentMarker?.showInfoWindow()

    }

    private fun getAddress(lat: Double, long: Double): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(lat, long, 1)
        return addresses[0].getAddressLine(0).toString()
    }

}