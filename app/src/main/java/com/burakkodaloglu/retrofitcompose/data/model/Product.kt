package com.burakkodaloglu.retrofitcompose.data.model

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double?,
    val title: String,
    var count: Int,
    var isFavorite: Boolean = false
)