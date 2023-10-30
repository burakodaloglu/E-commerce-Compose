package com.burakkodaloglu.retrofitcompose.ui.products

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

//Ana ekran yani ürünlerin listelendiği ekrana ait Route'u oluşturdum.
const val productsRoute = "products_route"

fun NavGraphBuilder.productsScreen(navigateToDetail: (Int) -> Unit) {
    composable(route = productsRoute) {
        ProductsRoute(navigateToDetail = navigateToDetail)
    }
}