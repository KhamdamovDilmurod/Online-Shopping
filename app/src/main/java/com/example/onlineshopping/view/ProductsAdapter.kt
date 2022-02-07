package com.example.onlineshopping.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshopping.R
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.screen.productsdetail.ProductDetailActivity
import com.example.onlineshopping.utils.Constants

class ProductsAdapter(val items: List<ProductModel>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textTitle = itemView.findViewById<TextView>(R.id.title)
        val imageView = itemView.findViewById<ImageView>(R.id.imagePhone)
        val textDesc = itemView.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,item)
            it.context.startActivity(intent)
        }

        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.image).into(holder.imageView)
        holder.textTitle.text = item.name
        holder.textDesc.text = item.price
    }

    override fun getItemCount(): Int {
        return items.size
    }
}