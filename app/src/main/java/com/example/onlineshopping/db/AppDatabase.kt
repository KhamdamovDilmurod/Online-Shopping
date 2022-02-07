package com.example.onlineshopping.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlineshopping.model.CartModel
import com.example.onlineshopping.model.CategoryModel
import com.example.onlineshopping.model.OfferModel
import com.example.onlineshopping.model.ProductModel

@Database(entities = [CategoryModel::class, ProductModel::class],  version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun initDatabase(context: Context) {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "online_shop_db"
                    ).build()
                }
            }
        }

        fun getDatabase(): AppDatabase {
            return INSTANCE!!
        }
    }
}