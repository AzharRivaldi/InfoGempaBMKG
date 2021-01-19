package com.azhar.informasigempa.views

import org.json.JSONObject

/**
 * Created by Azhar Rivaldi on 11-01-2021
 */

interface MainView {
    fun onGetDataJSON(response: JSONObject?)
    fun onNotice(pesanNotice: String?)
    fun onProses(proses: Boolean)
}