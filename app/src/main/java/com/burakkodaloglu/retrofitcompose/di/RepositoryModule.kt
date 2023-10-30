package com.burakkodaloglu.retrofitcompose.di

import com.burakkodaloglu.retrofitcompose.data.repository.ProductRepositoryImpl
import com.burakkodaloglu.retrofitcompose.data.source.remote.ProductService
import com.burakkodaloglu.retrofitcompose.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService): ProductRepository =
        ProductRepositoryImpl(productService)
}