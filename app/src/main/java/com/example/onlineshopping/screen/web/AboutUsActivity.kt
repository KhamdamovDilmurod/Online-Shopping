package com.example.onlineshopping.screen.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        binding.webView.webViewClient = WebViewClient()

        // this will load the url of the website
        // about us
        binding.webView.loadUrl("https://www.aboutamazon.com/about-us")

        // this will enable the javascript settings
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }
//nkj
    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }

}