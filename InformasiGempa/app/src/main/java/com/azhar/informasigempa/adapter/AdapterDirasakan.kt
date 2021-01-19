package com.azhar.informasigempa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azhar.informasigempa.R
import com.azhar.informasigempa.model.ModelGempaDirasakan
import kotlinx.android.synthetic.main.list_gempa_dirasakan.view.*
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Azhar Rivaldi on 10-01-2021
 */

class AdapterDirasakan(private val modelGempaDirasakan:
                       List<ModelGempaDirasakan>) : RecyclerView.Adapter<AdapterDirasakan.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_gempa_dirasakan, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelGempaDirasakan[position]
        var lastUpdate = data.strTanggal
        val formatDefault = SimpleDateFormat("dd/MM/yyyy-HH:mm:ss")
        val formatTime = SimpleDateFormat("EEE, dd MMM yyyy / HH:mm:ss")

        try {
            val timesFormatLast = formatDefault.parse(lastUpdate)
            lastUpdate = formatTime.format(timesFormatLast)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        holder.tvTanggal.text = lastUpdate
        holder.tvDirasakan.text = "Dirasakan : " + data.strDirasakan
        holder.tvKedalaman.text = "Kedalaman : " + data.strKedalaman
        holder.tvSkala.text = data.strMagnitude + "\nSR"
        holder.tvKeterangan.text = data.strKeterangan
        holder.tvPosisi.text = data.strKoordinat
    }

    override fun getItemCount(): Int {
        return modelGempaDirasakan.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTanggal: TextView
        var tvDirasakan: TextView
        var tvKedalaman: TextView
        var tvSkala: TextView
        var tvKeterangan: TextView
        var tvPosisi: TextView

        init {
            tvTanggal = itemView.tvTanggal
            tvDirasakan = itemView.tvDirasakan
            tvKedalaman = itemView.tvKedalaman
            tvSkala = itemView.tvSkala
            tvPosisi = itemView.tvPosisi
            tvKeterangan = itemView.tvKeterangan
        }
    }

}