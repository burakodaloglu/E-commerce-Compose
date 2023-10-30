package com.burakkodaloglu.retrofitcompose.ui.products

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.burakkodaloglu.retrofitcompose.R
import com.burakkodaloglu.retrofitcompose.data.model.Product
import com.burakkodaloglu.retrofitcompose.ui.components.ErrorScreen
import com.burakkodaloglu.retrofitcompose.ui.components.ProgressBar
import com.burakkodaloglu.retrofitcompose.ui.theme.Purple40

//Ana ekran yani ürünlerin listelendiği ekrana ait Route'un kontrolünü buradan yapıyorum.
@Composable
fun ProductsRoute(
    navigateToDetail: (Int) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val productsState by viewModel.state.collectAsStateWithLifecycle()
    val productsEffect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    ProductsScreen(
        state = productsState,
        effect = productsEffect,
        onProductClick = navigateToDetail
    )
}

@Composable
fun ProductsScreen(
    state: ProductUiState,
    effect: ProductEffect?,
    onProductClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ProgressBar(state.isLoading)

        if (state.product.isNotEmpty()) {
            ProductList(products = state.product, onProductClick = onProductClick)
        }
    }
    when (effect) {
        is ProductEffect.ShowError -> ErrorScreen(
            iconRes = R.drawable.ic_error,
            text = effect.message
        )

        else -> {}
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) {
            ProductItem(product = it, onProductClick = onProductClick)
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = Purple40,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onProductClick(product.id)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(96.dp)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            model = product.image,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = product.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            text = "${product.price} ₺",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}