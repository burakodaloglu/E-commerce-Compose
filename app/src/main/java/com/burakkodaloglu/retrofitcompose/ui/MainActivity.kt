package com.burakkodaloglu.retrofitcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.burakkodaloglu.retrofitcompose.ui.products.productsRoute
import com.burakkodaloglu.retrofitcompose.ui.theme.RetrofitComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    val navController = rememberNavController()
                    com.burakkodaloglu.retrofitcompose.ui.navigation.NavHost(
                        navController = navController,
                        startDestination = productsRoute
                    )
                }
            }
        }
    }
}