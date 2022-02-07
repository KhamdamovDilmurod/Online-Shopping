package com.example.onlineshopping.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshopping.model.ProductModel

@Dao
interface ProductDao {

    @Query("DELETE from products")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ProductModel>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductModel>
}