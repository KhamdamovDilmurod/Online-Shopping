package com.example.onlineshopping.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshopping.model.CategoryModel

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items:List<CategoryModel>)
    @Query("DELETE from categories")
    fun deleteAll()
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryModel>
}