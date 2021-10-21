package com.example.bedushop

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_adress.*
import kotlinx.android.synthetic.main.fragment_adress.view.*
import java.util.*


class AddressFragment : BottomSheetDialogFragment(){
    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.fragment_adress, container, false)
        view.closeBtn.setOnClickListener{
            dismiss()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())



        view.ActualizarDireccion.setOnClickListener{
            getCurrentLocation()
        }
        view.btOpenMap.setOnClickListener {
            openMap()
        }
        return view
    }
    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // request permission
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);

            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                latitude = location.latitude
                longitude = location.longitude

                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                actualAdressText.text = "${addresses[0].locality} ${addresses[0].countryName} "
                tvLatitude.text = "Latitude: ${location.latitude}"
                tvLongitude.text = "Longitude: ${location.longitude}"
                //vProvider.text = "Provider: ${location.provider}"

                btOpenMap.visibility = View.VISIBLE
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(context, "You need to grant permission to access location",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun openMap() {
        val uri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }


}