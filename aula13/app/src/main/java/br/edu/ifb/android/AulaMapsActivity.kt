package br.edu.ifb.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class AulaMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private lateinit var mMap: GoogleMap

    private var mGoogleApiClient: GoogleApiClient? = null

    private var mHandler: Handler? = null
    private var nTentativas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aula_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addApi(LocationServices.API)
            .addApi(Places.GEO_DATA_API)
            .addApi(Places.PLACE_DETECTION_API)
            .build()

        mHandler = Handler()

        val placeAutocompleteFragment = fragmentManager
            .findFragmentById(R.id.place_autocomplete_fragment)
                as PlaceAutocompleteFragment

        placeAutocompleteFragment.setOnPlaceSelectedListener(
            object : PlaceSelectionListener {
                override fun onPlaceSelected(p0: Place?) {
                    atualizarMapa(p0?.latLng!!)
                }

                override fun onError(p0: Status?) {
                    Log.e("ERRO", "Não foi possível obter a localização: $p0")
                }

            }
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onConnected(p0: Bundle?) {
        verificarStatusGps()
//        obterLocalizacao()
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient?.connect()
    }

    override fun onResume() {
        super.onResume()
        mGoogleApiClient!!.connect()
    }

    override fun onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            mGoogleApiClient!!.disconnect()
        }
        mHandler?.removeCallbacksAndMessages(null)
        super.onStop()
    }

    private fun atualizarMapa(local: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 20.0f))
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(local).title("Local Atual!"))
    }

    private fun obterLocalizacao() {
        if (
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            &&
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        } else {
            val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
            if (location != null) {
                nTentativas = 0
                atualizarMapa(
                    LatLng(
                        location.latitude,
                        location.longitude
                    )
                )
            } else if (nTentativas < 5) {
                nTentativas++
                mHandler?.postDelayed({
                    obterLocalizacao()
                }, 1000)
            }
        }
    }

    private fun verificarStatusGps() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val locatSettingsRequest = LocationSettingsRequest.Builder()
        locatSettingsRequest.setAlwaysShow(true)
        locatSettingsRequest.addLocationRequest(locationRequest)
        val result: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(
                mGoogleApiClient,
                locatSettingsRequest.build()
            )
        result.setResultCallback { p0 ->
            val status = p0.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> obterLocalizacao()
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    status.startResolutionForResult(this@AulaMapsActivity, 2)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    Log.i("MAPS-APP", "Não foi possível obter a localização")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                nTentativas = 0
                mHandler?.removeCallbacksAndMessages(null)
                obterLocalizacao()
            }
        }
    }


}
