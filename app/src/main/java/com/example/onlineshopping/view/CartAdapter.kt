package com.example.onlineshopping.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshopping.databinding.CartItemLayoutBinding
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.utils.Constants
import com.example.onlineshopping.utils.PrefUtils

class CartAdapter(val items:List<ProductModel>):RecyclerView.Adapter<CartAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: CartItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.price.text = item.price
        holder.binding.name.text = item.name
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.image).into(holder.binding.image)

        holder.binding.count.text = item.cartCount.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}