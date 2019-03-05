package com.example.weatherpractical.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherpractical.R
import com.example.weatherpractical.db.City
import kotlinx.android.synthetic.main.item_city.view.*


class CityListAdapter(private val cityList: List<City>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<CityListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(cityList[position], listener)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: City, listener: (Int) -> Unit) = with(itemView) {
            textViewCity.text = item.name
            textView3.text = "280.0F"
            itemView.setOnClickListener { listener(adapterPosition) }
        }
    }
}