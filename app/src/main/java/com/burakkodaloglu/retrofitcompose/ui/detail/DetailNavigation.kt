package com.burakkodaloglu.retrofitcompose.ui.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val detailRoute = "detail_route"

fun NavController.navigateToDetail(
    productId: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        detailRoute.plus("/$productId"),
        navOptions
    )
}

fun NavGraphBuilder.detailScreen(
    navigateBack: () -> Unit
) {
    composable(
        route = detailRoute.plus("/{productId}")
    ) {
        DetailRoute(navigateBack = navigateBack)
    }
}