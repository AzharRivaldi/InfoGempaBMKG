package com.azhar.informasigempa.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.ParseException
import java.text.SimpleDateFormat

class FragmentDetailGempa : BottomSheetDialogFragment(), OnMapReadyCallback {

    var googleMaps: GoogleMap? = null
    var strLat: String? = null
    var strLong: String? = null
    var strWilayah: String? = null
    var strKedalaman: String? = null
    var strSkala: String? = null
    var strTanggal: String? = null
    var latitude = 0.0
    var longitude = 0.0

    companion object {
        private const val ARGUMENT_LAT = "StrLintang"
        private const val ARGUMENT_LONG = "StrBujur"
        private const val ARGUMENT_TANGGAL = "StrTanggal"
        private const val ARGUMENT_SKALA = "StrMagnitude"
        private const val ARGUMENT_KEDALAMAN = "StrKedalaman"
        private const val ARGUMENT_WILAYAH = "StrWilayah"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_gempa, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        strLat = arguments?.getString(ARGUMENT_LAT)
        strLong = arguments?.getString(ARGUMENT_LONG)
        strWilayah = arguments?.getString(ARGUMENT_WILAYAH)
        strKedalaman = arguments?.getString(ARGUMENT_KEDALAMAN)
        strSkala = arguments?.getString(ARGUMENT_SKALA)
        strTanggal = arguments?.getString(ARGUMENT_TANGGAL)

        val formatDefault = SimpleDateFormat("dd-MMM-yy")
        val formatTime = SimpleDateFormat("EEE, dd MMM yyyy")
        try {
            val timesFormatLast = formatDefault.parse(strTanggal)
            strTanggal = formatTime.format(timesFormatLast)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //show maps
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        tvKedalaman.text = "Kedalaman\n $strKedalaman"
        tvSkala.text = "Magnitude\n $strSkala"
        tvTanggal.text = "Tanggal\n $strTanggal"

        fabClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        latitude = "-$strLat".toDouble()
        longitude = strLong.toDouble()

        googleMaps = googleMap

        val latLng = LatLng(latitude, longitude)
        googleMaps?.addMarker(MarkerOptions().position(latLng).title(strWilayah))
        googleMaps?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMaps?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
        googleMaps?.uiSettings?.setAllGesturesEnabled(true)
        googleMaps?.uiSettings?.isZoomGesturesEnabled = true
        googleMaps?.isTrafficEnabled = true
    }

}
