package com.example.onlineshopping.screen.profil

import android.app.Application
import androidx.multidex.MultiDex
import com.example.onlineshopping.db.AppDatabase
import com.orhanobut.hawk.Hawk

class MyApp: Application(){
    companion object{
        lateinit var app: MyApp

    }

    override fun onCreate() {
        super.onCreate()
        app = this
        MultiDex.install(app)
        Hawk.init(app).build()
        AppDatabase.initDatabase(app)
    }
}