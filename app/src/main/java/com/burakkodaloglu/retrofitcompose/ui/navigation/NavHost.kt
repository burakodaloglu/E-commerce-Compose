package com.burakkodaloglu.retrofitcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.burakkodaloglu.retrofitcompose.ui.detail.detailScreen
import com.burakkodaloglu.retrofitcompose.ui.detail.navigateToDetail
import com.burakkodaloglu.retrofitcompose.ui.products.productsScreen

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    //NavGraph xml oluşturuldu.
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        productsScreen(
            navigateToDetail = { navController.navigateToDetail(it) }
        )
        detailScreen(navigateBack = navController::navigateUp)
    }
}