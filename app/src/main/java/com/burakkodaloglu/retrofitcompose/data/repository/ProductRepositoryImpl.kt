package com.burakkodaloglu.retrofitcompose.data.repository

import com.burakkodaloglu.retrofitcompose.common.Resource
import com.burakkodaloglu.retrofitcompose.data.model.Product
import com.burakkodaloglu.retrofitcompose.data.source.remote.ProductService
import com.burakkodaloglu.retrofitcompose.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productService: ProductService) : ProductRepository {
    override suspend fun getProduct(): Resource<List<Product>> =
        try {
            Resource.Success(productService.getProducts())
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }

    override suspend fun getProductId(productId: Int): Resource<Product> =
        try {
            Resource.Success(productService.getProductById(productId))
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
}