package com.example.onlineshopping.screen.productsdetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.ActivityProductDetailBinding
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.utils.Constants
import com.example.onlineshopping.utils.LocaleManager
import com.example.onlineshopping.utils.PrefUtils

class ProductDetailActivity : AppCompatActivity() {

    lateinit var item: ProductModel

    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.cardViewBack.setOnClickListener {
            finish()
        }

        binding.cardViewFavourite.setOnClickListener {
            PrefUtils.setFavourite(item)

            if (PrefUtils.checkFavourite(item)){
                binding.cardViewFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                binding.cardViewFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        binding.tvTitle.text = item.name
        binding.productName.text = item.name
        binding.productPrice.text = "${item.price} so'm"

        if (PrefUtils.checkFavourite(item)){
            binding.cardViewFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            binding.cardViewFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(binding.image)

        if (PrefUtils.getCartCount(item)>0){
            binding.buttonCart.visibility = View.GONE
        }

        binding.buttonCart.setOnClickListener {
            item.cartCount = 1
            PrefUtils.setCart(item)
            Toast.makeText(this, "Product added to cart!",Toast.LENGTH_LONG).show()
            finish()
        }
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}