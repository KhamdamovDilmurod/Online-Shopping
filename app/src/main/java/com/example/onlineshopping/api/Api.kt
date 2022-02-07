package com.example.onlineshopping.api

import com.example.onlineshopping.model.BaseResponse
import com.example.onlineshopping.model.CategoryModel
import com.example.onlineshopping.model.OfferModel
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.model.request.RequestModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_top_products")
    fun getProducts(): Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") categoryId: Int): Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: RequestModel): Observable<BaseResponse<List<ProductModel>>>

}