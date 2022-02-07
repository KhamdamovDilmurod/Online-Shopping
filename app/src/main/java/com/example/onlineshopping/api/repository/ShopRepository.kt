package com.example.onlineshopping.api.repository


import androidx.lifecycle.MutableLiveData
import com.example.onlineshopping.api.ApiService
import com.example.onlineshopping.model.BaseResponse
import com.example.onlineshopping.model.CategoryModel
import com.example.onlineshopping.model.OfferModel
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.model.request.RequestModel
import com.example.onlineshopping.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class ShopRepository() {
    val compositeDisposable = CompositeDisposable()

    fun getProductsByCategory(id: Int, error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>){
        compositeDisposable.add(
            ApiService.apiClient().getCategoryProducts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getProducts(error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>){
        compositeDisposable.add(
            ApiService.apiClient().getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
    fun getOffers(error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<OfferModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<OfferModel>>>(){
                    override fun onNext(t: BaseResponse<List<OfferModel>>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                       }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                       error.value = e.localizedMessage
                     }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getCategories(error: MutableLiveData<String>, success: MutableLiveData<List<CategoryModel>>){
        compositeDisposable.add(
            ApiService.apiClient().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<CategoryModel>>>(){
                    override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
    fun getProductsByIds(ids: List<Int>, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<ProductModel>>){
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getProductsByIds(RequestModel(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        progress.value = false
                        if (t.success){
                            PrefUtils.getCartList().forEach { cartProducts ->
                                val carts =
                                    t.data.filter { cartProducts.product_id == it.id }.firstOrNull()
                                if (carts != null) {
                                    carts.cartCount = cartProducts.count
                                } else {
//                                    error.value = t.message
                                }
                            }
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
}