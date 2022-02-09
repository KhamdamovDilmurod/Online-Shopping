package com.example.onlineshopping.model.request

import com.example.onlineshopping.model.CartModel

data class MakeOrderRequest(
    val products: List<CartModel>,
    val order_type: String,
    val adress: String,
    val lat: Double,
    val lon: Double,
    val comment: String
)