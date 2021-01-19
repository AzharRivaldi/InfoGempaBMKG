package com.azhar.informasigempa.views

/**
 * Created by Azhar Rivaldi on 11-01-2021
 */

interface MainPresenter {
    fun getDataGempaDirasakan()
    fun getDataGempaBerpotensi()
    fun onProses(proses: Boolean)
}