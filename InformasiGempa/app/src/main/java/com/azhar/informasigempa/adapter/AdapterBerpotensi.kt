package com.azhar.informasigempa.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.azhar.informasigempa.R
import com.azhar.informasigempa.fragment.FragmentDetailGempa
import com.azhar.informasigempa.model.ModelGempaBerpotensi
import kotlinx.android.synthetic.main.list_gempa_berpotensi.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Azhar Rivaldi on 14-01-2021
 */

class AdapterBerpotensi(private val modelGempaBerpotensi:
                        List<ModelGempaBerpotensi>) : RecyclerView.Adapter<AdapterBerpotensi.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_gempa_berpotensi, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelGempaBerpotensi[position]
        var lastUpdate = data.strTanggal
        val formatDefault = SimpleDateFormat("dd-MMM-yy")
        val formatTime = SimpleDateFormat("EEE, dd MMM yyyy")

        try {
            val timesFormatLast = formatDefault.parse(lastUpdate)
            lastUpdate = formatTime.format(timesFormatLast)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val skalaPotensi = data.strMagnitude?.replace(" SR", "")?.toDouble()
        val strPotensiTsunami: String
        strPotensiTsunami = if (skalaPotensi!! > 7.0) {
            "Berpotensi Tsunami"
        } else {
            "Tidak Berpotensi Tsunami"
        }

        holder.tvTanggal.text = lastUpdate + " " + data.strWaktu
        holder.tvWilayah.text = data.strWilayah
        holder.tvKedalaman.text = "Kedalaman : " + data.strKedalaman
        holder.tvSkala.text = data.strMagnitude

        if (skalaPotensi > 0) {
            holder.tvPotensi.text = strPotensiTsunami
        } else {
            holder.tvPotensi.text = strPotensiTsunami
        }

        holder.tvSkala.setOnClickListener { view ->
            val fragment: Fragment = FragmentDetailGempa()
            val bundle = Bundle()
            bundle.putString("StrLintang", data.strLintang)
            bundle.putString("StrBujur", data.strBujur)
            bundle.putString("StrTanggal", data.strTanggal)
            bundle.putString("StrMagnitude", data.strMagnitude)
            bundle.putString("StrKedalaman", data.strKedalaman)
            bundle.putString("StrWilayah", data.strWilayah)
            fragment.arguments = bundle
            val fragmentManager = (view.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount(): Int {
        return modelGempaBerpotensi.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTanggal: TextView
        var tvKedalaman: TextView
        var tvSkala: TextView
        var tvWilayah: TextView
        var tvPotensi: TextView

        init {
            tvTanggal = itemView.tvTanggal
            tvKedalaman = itemView.tvKedalaman
            tvSkala = itemView.tvSkala
            tvWilayah = itemView.tvWilayah
            tvPotensi = itemView.tvPotensi
        }
    }

}