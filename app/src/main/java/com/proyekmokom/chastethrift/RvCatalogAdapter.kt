package com.proyekmokom.chastethrift

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RvCatalogAdapter (
    private val itemList: List<ItemEntity>,
    private val context: Context,
    var toDetail: (id: Int?) -> Unit
) :
    RecyclerView.Adapter<RvCatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalog_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val index1 = position * 2
        val index2 = position * 2 + 1

        if (index1 < itemList.size) {
            val item1 = itemList[index1]

            Glide.with(context).load(item1.gambar).into(holder.image1)

//            holder.image1.setImageResource(R.drawable.kucing) // Or use item1.imageResource if you have image resources in your ItemEntity
            holder.name1.text = item1.nama
            holder.price1.text = "Rp ${item1.harga}"
            holder.image1.visibility = View.VISIBLE
            holder.name1.visibility = View.VISIBLE
            holder.price1.visibility = View.VISIBLE

            holder.layout1.setOnClickListener {
                toDetail(item1.id_item)
            }
        } else {
            holder.image1.visibility = View.GONE
            holder.name1.visibility = View.GONE
            holder.price1.visibility = View.GONE
            holder.layout1.setOnClickListener(null)
        }

        if (index2 < itemList.size) {
            val item2 = itemList[index2]

            Glide.with(context).load(item2.gambar).into(holder.image2)

//            holder.image2.setImageResource(R.drawable.gucci) // Or use item2.imageResource if you have image resources in your ItemEntity
            holder.name2.text = item2.nama
            holder.price2.text = "Rp ${item2.harga}"
            holder.image2.visibility = View.VISIBLE
            holder.name2.visibility = View.VISIBLE
            holder.price2.visibility = View.VISIBLE

            holder.layout2.setOnClickListener {
                toDetail(item2.id_item)
            }
        } else {
            holder.image2.visibility = View.GONE
            holder.name2.visibility = View.GONE
            holder.price2.visibility = View.GONE
            holder.layout2.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int {
        return (itemList.size + 1) / 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image1: ImageView = itemView.findViewById(R.id.image1)
        val name1: TextView = itemView.findViewById(R.id.name1)
        val price1: TextView = itemView.findViewById(R.id.price1)
        val image2: ImageView = itemView.findViewById(R.id.image2)
        val name2: TextView = itemView.findViewById(R.id.name2)
        val price2: TextView = itemView.findViewById(R.id.price2)
        val layout1: LinearLayout = itemView.findViewById(R.id.layout_1)
        val layout2: LinearLayout = itemView.findViewById(R.id.layout_2)
    }
}
