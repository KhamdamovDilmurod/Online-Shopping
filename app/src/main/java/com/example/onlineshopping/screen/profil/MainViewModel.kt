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

    private val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()
    val progress = MutableLiveData<Boolean>()

    // github ->
    val checkPhoneData = MutableLiveData<CheckPhoneResponse>()
    val registrationData = MutableLiveData<Boolean>()
    val confirmData = MutableLiveData<LoginResponse>()
    val loginData = MutableLiveData<LoginResponse>()
    val makeOrderData = MutableLiveData<Boolean>()
    // <- //

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

    // github ->
    fun checkPhone(phone: String){
        repository.checkPhone(phone, error, progress, checkPhoneData)
    }

    fun registrationData(fullname: String, phone: String, password: String){
        repository.registration(fullname, phone, password, error, progress, registrationData)
    }

    fun login(phone: String, password: String){
        repository.login(phone, password, error, progress, loginData)
    }

    fun confirmUser(phone: String, code: String){
        repository.confirmUser(phone, code, error, progress, confirmData)
    }

    fun makeOrder(products: List<CartModel>, lat: Double, lon: Double, comment: String){
        repository.makeOrder(products, lat, lon, comment, error, progress, makeOrderData)
    }
    // <- //
}