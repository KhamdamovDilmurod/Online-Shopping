package com.example.onlineshopping.screen.makeorder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshopping.MapsActivity
import com.example.onlineshopping.databinding.ActivityMakeOrderBinding
import com.example.onlineshopping.model.AddressModel
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.screen.profil.MainViewModel
import com.example.onlineshopping.utils.Constants
import com.example.onlineshopping.utils.LocaleManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeOrderBinding
    lateinit var viewModel: MainViewModel
    var address: AddressModel? = null
    lateinit var items: List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })


        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

        binding.tvTotalAmount.text = items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0) }.toString()

        binding.edAddress.setOnClickListener {
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
        binding.edAddress.setText("${address.latitude}, ${address.longitude}")
    }
}