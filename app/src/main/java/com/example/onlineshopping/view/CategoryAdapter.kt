package com.example.onlineshopping.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.CategoryItemLayoutBinding
import com.example.onlineshopping.model.CategoryModel

interface CategoryAdapterCallback {
    fun onClickItem(item:CategoryModel)
}

class CategoryAdapter(val items: List<CategoryModel>, val callback: CategoryAdapterCallback):RecyclerView.Adapter<CategoryAdapter.ItemHolder>(){

    inner class ItemHolder(val binding: CategoryItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            items.forEach {
                it.checked=false
            }
            item.checked = true

            callback.onClickItem(item)
            notifyDataSetChanged()
        }
        holder.binding.tvTitle.text = item.title

        if (item.checked){
            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
            holder.binding.tvTitle.setTextColor(Color.WHITE )
        } else{
            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
            holder.binding.tvTitle.setTextColor(Color.BLACK )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}