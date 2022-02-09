package com.example.onlineshopping.utils

import com.example.onlineshopping.model.CartModel
import com.example.onlineshopping.model.ProductModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    const val PREF_FAVOURITES = "pref_favourites"
    const val PREF_CART = "pref_cart"
    const val PREF_TOKEN= "pref_token"
    const val PREF_FCM_TOKEN= "pref_fcm_token"

    fun setFavourite(item: ProductModel){
        val items = Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
        if (items.filter { it == item.id }.firstOrNull() != null){
            items.remove(item.id)
        }else{
            items.add(item.id)
        }
        Hawk.put(PREF_FAVOURITES,items)
    }

    fun getFavouriteList(): ArrayList<Int>{
        return Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
    }

    fun checkFavourite(item: ProductModel): Boolean{
        val items = Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }

    fun setCart(item: ProductModel){
        val items = Hawk.get(PREF_CART, arrayListOf<CartModel>())
        val cart = items.filter{it.product_id == item.id}.firstOrNull()
        if (cart!=null){
            if (item.cartCount>0){
                cart.count = item.cartCount
            }else{
                items.remove(cart)
            }
        }else{
            val newCart = CartModel(item.id, item.cartCount)
            items.add(newCart)
        }
        Hawk.put(PREF_CART,items)

    }

    fun getCartList():ArrayList<CartModel>{
        return Hawk.get(PREF_CART, arrayListOf<CartModel>())
    }

    fun getCartCount(item: ProductModel): Int{
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf<CartModel>())
        return items.filter{ it.product_id == item.id}.firstOrNull()?.count ?: 0
    }

    fun setFCMToken(value: String){
        Hawk.put(PREF_FCM_TOKEN, value)
    }

    fun getFCMToken(): String{
        return Hawk.get(PREF_FCM_TOKEN, "")
    }
    fun setToken(value: String){
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String{
        return Hawk.get(PREF_TOKEN, "")
    }

}