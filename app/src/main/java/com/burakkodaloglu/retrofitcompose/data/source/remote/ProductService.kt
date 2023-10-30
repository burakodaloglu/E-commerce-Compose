package com.burakkodaloglu.retrofitcompose.data.source.remote

import com.burakkodaloglu.retrofitcompose.data.model.Product
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @POST("products")
    suspend fun getProductById(productId: Int): Product
}