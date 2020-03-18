package com.example.uts_dani

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BeritaAdapter (val beritaList: ArrayList<Berita>): RecyclerView.Adapter<BeritaAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: Berita = beritaList[position]
        holder?.textViewJudul?.text = user.judul
        holder?.textViewWaktu?.text = user.waktu
        holder?.textViewPenulis?.text = user.penulis
        holder?.textViewIsi?.text = user.isi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_berita, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return beritaList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewJudul = itemView.findViewById(R.id.textViewJudul) as TextView
        val textViewWaktu = itemView.findViewById(R.id.textViewWaktu) as TextView
        val textViewPenulis = itemView.findViewById(R.id.textViewPenulis) as TextView
        val textViewIsi = itemView.findViewById(R.id.textViewIsi) as TextView
    }
}