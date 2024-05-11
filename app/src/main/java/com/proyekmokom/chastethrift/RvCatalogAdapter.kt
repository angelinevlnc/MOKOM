package com.proyekmokom.chastethrift

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvCatalogAdapter(private val itemList: List<FakeList>) :
    RecyclerView.Adapter<RvCatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalog_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item1 = itemList[position * 2]
        val item2 = itemList[position * 2 + 1]

        // Set gambar untuk item 1 dan item 2
        holder.itemView.findViewById<ImageView>(R.id.image1).setImageResource(item1.gambar)
        holder.itemView.findViewById<ImageView>(R.id.image2).setImageResource(item2.gambar)

        // Set nama dan harga untuk item 1 dan item 2
        holder.itemView.findViewById<TextView>(R.id.name1).text = item1.nama
        holder.itemView.findViewById<TextView>(R.id.price1).text = "Rp ${item1.harga}"

        holder.itemView.findViewById<TextView>(R.id.name2).text = item2.nama
        holder.itemView.findViewById<TextView>(R.id.price2).text = "Rp ${item2.harga}"
    }

    override fun getItemCount(): Int {
        // Mengembalikan jumlah item yang sesuai dengan jumlah pasangan dalam daftar palsu
        return (itemList.size + 1) / 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
