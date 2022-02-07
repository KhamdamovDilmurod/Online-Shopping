package com.example.onlineshopping.screen.makeorder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshopping.MapsActivity
import com.example.onlineshopping.databinding.ActivityMakeOrderBinding
import com.example.onlineshopping.model.AddressModel
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.utils.Constants
import com.example.onlineshopping.utils.LocaleManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeOrderBinding

    var address: AddressModel? = null
    lateinit var items: List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

        binding.totalAmount.text = items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0) }.toString()

        binding.etAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.cardViewBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(address: AddressModel){
        this.address = address
        binding.etAddress.setText("${address.latitude}, ${address.longitude}")
    }
}