package com.example.onlineshopping.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryModel (
    @PrimaryKey(autoGenerate = true) // shu qismi qop ketsa faqat bitta elementdi korsatadi
    val uid: Int,
    val id: Int,
    val title: String,
    val icon: String,
    var checked: Boolean = false
        )