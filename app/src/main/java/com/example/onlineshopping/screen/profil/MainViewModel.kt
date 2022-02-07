package com.example.onlineshopping.screen.profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshopping.api.repository.ShopRepository
import com.example.onlineshopping.db.AppDatabase
import com.example.onlineshopping.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()
    val progress = MutableLiveData<Boolean>()

    fun getOffers(){
        repository.getOffers(error,progress, offersData)
    }
    fun getCategories(){
        repository.getCategories(error,categoriesData)
    }
    fun getProducts(){
        repository.getProducts(error, productsData)
    }

    fun getProductsByCategory(id: Int){
        repository.getProductsByCategory(id,error,productsData)
    }

    fun getProductsByIds(ids: List<Int>){
        repository.getProductsByIds(ids, error,progress, productsData)
    }

    fun insertAllProducts2DB(items: List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getProductDao().deleteAll()
            AppDatabase.getDatabase().getProductDao().insertAll(items)
        }
    }

    fun insertAllCategories2DB(items: List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getCategoryDao().deleteAll()
            AppDatabase.getDatabase().getCategoryDao().insertAll(items)
        }
    }
    // comment qo'shildi




    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch{
            productsData.value = withContext(Dispatchers.IO){
                AppDatabase.getDatabase().getProductDao().getAllProducts()}
        }

    }

    fun getAllDBCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            categoriesData.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase().getCategoryDao().getAllCategories()
            }
        }
    }
}