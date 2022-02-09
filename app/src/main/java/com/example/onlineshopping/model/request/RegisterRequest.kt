package com.example.onlineshopping.model.request

data class RegisterRequest(
    val fullname: String,
    val phone: String,
    val password: String
)