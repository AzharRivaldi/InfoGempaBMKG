package com.azhar.informasigempa.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.informasigempa.R
import com.azhar.informasigempa.networking.ApiEndpoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_terkini.*
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat

class FragmentTerkini : Fragment(), OnMapReadyCallback {

    var progressDialog: ProgressDialog? = null
    var strLat: String? = null
    var strLong: String? = null
    var strPotensi: String? = null
    var strTanggal: String? = null
    var strWaktu: String? = null
    var strSkala: String? = null
    var strKedalaman: String? = null
    var strWilayah1: String? = null
    var strWilayah2: String? = null
    var strWilayah3: String? = null
    var strWilayah4: String? = null
    var strWilayah5: String? = null
    var latitude = 0.0
    var longitude = 0.0
    var googleMaps: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_terkini, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setTitle("Mohon tunggu")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang mengambil data...")

        //show maps
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun getDataGempaTerkini() {
        AndroidNetworking.get(ApiEndpoint.URL_GEMPA_M5)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(response: JSONObject) {
                        try {
                            val jsonArray = response.getJSONArray("features")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val jsonObjectData = jsonObject.getJSONObject("properties")
                                strPotensi = jsonObjectData.getString("potensi")
                                strTanggal = jsonObjectData.getString("tanggal")
                                strWaktu = jsonObjectData.getString("jam")
                                strLat = jsonObjectData.getString("lintang").replace(" LU", "").replace(" LS", "")
                                strLong = jsonObjectData.getString("bujur").replace(" BT", "")
                                strSkala = jsonObjectData.getString("magnitude")
                                strKedalaman = jsonObjectData.getString("kedalaman")
                                strWilayah1 = jsonObjectData.getString("wilayah1")
                                strWilayah2 = jsonObjectData.getString("wilayah2")
                                strWilayah3 = jsonObjectData.getString("wilayah3")
                                strWilayah4 = jsonObjectData.getString("wilayah4")
                                strWilayah5 = jsonObjectData.getString("wilayah5")

                                val formatDefault = SimpleDateFormat("dd-MMM-yy")
                                val formatTime = SimpleDateFormat("EEE, dd MMM yyyy")

                                try {
                                    val timesFormatLast = formatDefault.parse(strTanggal)
                                    strTanggal = formatTime.format(timesFormatLast)
                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                }

                                latitude = "-$strLat".toDouble()
                                longitude = strLong!!.toDouble()

                                val latLng = LatLng(latitude, longitude)
                                googleMaps?.addMarker(MarkerOptions().position(latLng))
                                googleMaps?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                                googleMaps?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
                                googleMaps?.uiSettings?.setAllGesturesEnabled(true)
                                googleMaps?.uiSettings?.isZoomGesturesEnabled = true
                                googleMaps?.isTrafficEnabled = true

                                tvPotensi.text = strPotensi
                                tvTanggal.text = "$strTanggal / $strWaktu"
                                tvSkala.text = "Skala : $strSkala"
                                tvKedalaman.text = "Kedalaman : $strKedalaman"
                                tvWilayah1.text = strWilayah1
                                tvWilayah2.text = strWilayah2
                                tvWilayah3.text = strWilayah3
                                tvWilayah4.text = strWilayah4
                                tvWilayah5.text = strWilayah5
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(activity, "Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(anError: ANError) {
                        Toast.makeText(activity, "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMaps = googleMap
        getDataGempaTerkini()
    }

}