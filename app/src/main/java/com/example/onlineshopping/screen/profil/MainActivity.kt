package com.example.onlineshopping.screen.profil

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.ActivityMainBinding
import com.example.onlineshopping.screen.fragments.CartFragment
import com.example.onlineshopping.screen.fragments.FavouriteFragment
import com.example.onlineshopping.screen.fragments.HomeFragment
import com.example.onlineshopping.screen.fragments.UserFragment
import com.example.onlineshopping.screen.settings.ChangeLanguageFragment
import com.example.onlineshopping.screen.sign.LoginActivity
import com.example.onlineshopping.screen.web.AboutUsActivity
import com.example.onlineshopping.screen.web.WebViewActivity
import com.example.onlineshopping.utils.LocaleManager
import com.example.onlineshopping.utils.PrefUtils

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    val homeFragment = HomeFragment()
    val favouriteFragment = FavouriteFragment()
    val cartFragment = CartFragment()
    val userFragment = UserFragment()
    var activeFragment: Fragment =   homeFragment

    private lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = MainViewModel()

        viewModel.productsData.observe(this, Observer {
            viewModel.insertAllProducts2DB(it)
            homeFragment.loadData()
        })

        viewModel.categoriesData.observe(this, Observer {
            viewModel.insertAllCategories2DB(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,binding.toolBar,
            R.string.open,R.string.close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportFragmentManager.beginTransaction().add(R.id.frame_container,homeFragment,homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container,favouriteFragment,favouriteFragment.tag).hide(favouriteFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container,cartFragment,cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container,userFragment,userFragment.tag).hide(userFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId== R.id.home){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
            }
            else if (it.itemId== R.id.favourite){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(favouriteFragment).commit()
                activeFragment = favouriteFragment
            }
            else if (it.itemId== R.id.korzinka){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment).commit()
                activeFragment = cartFragment
            }
            else if (it.itemId== R.id.user){
                if (PrefUtils.getToken().isNullOrEmpty()){
                    startActivity(Intent(this, LoginActivity::class.java))
                    return@setOnNavigationItemSelectedListener false
                }else{
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(userFragment).commit()
                    activeFragment = userFragment
                }
            }
            return@setOnNavigationItemSelectedListener true
        }


        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.aboutUs -> {
                    val intent = Intent(this,AboutUsActivity::class.java)
                    startActivity(intent)
                }
                R.id.dashboard -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
                R.id.notification -> {
                    val intent = Intent(this,WebViewActivity::class.java)
                    startActivity(intent)
                }
                R.id.call -> {
                    startPhoneActionIntent("911210322")
                }
                R.id.smile -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
                R.id.settings -> {
                    val fragment = ChangeLanguageFragment.newInstance()
                    fragment.show(supportFragmentManager, fragment.tag)
                }
            }
            return@setNavigationItemSelectedListener true
        }

        loadData()
    }

    fun loadData(){
        viewModel.getProducts()
        viewModel.getCategories()
        viewModel.getOffers()
    }

    private fun startPhoneActionIntent(phoneCode: String) {
        val requestCode = 1
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneCode"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    requestCode
                )
            } else {
                startActivity(intent)
            }
        } else {
            startActivity(intent)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "Mening ilovamni sinab ko'ring va baho bering: t.me//Hamdamov_Dilmurod")
                startActivity(intent)
            }
            R.id.nav_telegram -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "Mening ilovamni sinab ko'ring va baho bering: https://github.com/KhamdamovDilmurod/Online-Shopping")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

}