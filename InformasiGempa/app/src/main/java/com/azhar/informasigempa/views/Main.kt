package com.azhar.informasigempa.views

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.informasigempa.networking.ApiEndpoint
import org.json.JSONObject

/**
 * Created by Azhar Rivaldi on 11-01-2021
 */

class Main(var mainView: MainView) : MainPresenter {

    override fun getDataGempaDirasakan() {
        mainView.onProses(true)
        AndroidNetworking.get(ApiEndpoint.URL_GEMPA_DIRASAKAN)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        mainView.onProses(false)
                        mainView.onGetDataJSON(response)
                    }

                    override fun onError(anError: ANError) {
                        mainView.onProses(false)
                        mainView.onNotice("Gagal mendapatkan data!")
                    }
                })
    }

    override fun getDataGempaBerpotensi() {
        mainView.onProses(true)
        AndroidNetworking.get(ApiEndpoint.URL_GEMPA_BERPOTENSI)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        mainView.onProses(false)
                        mainView.onGetDataJSON(response)
                    }

                    override fun onError(anError: ANError) {
                        mainView.onProses(false)
                        mainView.onNotice("Gagal mendapatkan data!")
                    }
                })
    }

    override fun onProses(proses: Boolean) {
        mainView.onProses(proses)
    }

}