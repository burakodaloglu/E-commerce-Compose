package com.burakkodaloglu.retrofitcompose.domain.repository

import com.burakkodaloglu.retrofitcompose.common.Resource
import com.burakkodaloglu.retrofitcompose.data.model.Product

interface ProductRepository {
    suspend fun getProduct(): Resource<List<Product>>
    suspend fun getProductId(productId: Int): Resource<Product>
}