package com.burakkodaloglu.retrofitcompose.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.burakkodaloglu.retrofitcompose.R
import com.burakkodaloglu.retrofitcompose.data.model.Product
import com.burakkodaloglu.retrofitcompose.ui.components.ErrorScreen
import com.burakkodaloglu.retrofitcompose.ui.components.ProgressBar
import com.burakkodaloglu.retrofitcompose.ui.theme.Purple40
import com.burakkodaloglu.retrofitcompose.ui.theme.Purple80

@Composable
fun DetailRoute(navigateBack: () -> Unit, viewModel: DetailViewModel = hiltViewModel()) {
    val detailState by viewModel.state.collectAsStateWithLifecycle()
    val detailEffect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    DetailScreen(
        state = detailState,
        effect = detailEffect,
        onBackClick = { navigateBack() },
        onTriggerEvent = viewModel::setEvent
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    effect: DetailEffect?,
    onBackClick: () -> Unit,
    onTriggerEvent: (DetailEvent) -> Unit
) {
    ProgressBar(state.isLoading)
    if (state.product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            TopBar(
                likeState = state.favState,
                onFavClick = { onTriggerEvent(DetailEvent.FavClicked) },
                onBackClick = onBackClick
            )
            ProductDetail(product = state.product)
        }
    }
    when (effect) {
        is DetailEffect.ShowError -> ErrorScreen(
            iconRes = R.drawable.ic_error,
            text = effect.message
        )

        else -> {}
    }
}


@Composable
fun TopBar(
    likeState: Boolean,
    onFavClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(32.dp)
                .clickable { onBackClick() },
            imageVector = Icons.Rounded.ArrowBack,
            tint = Purple40,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
                .clickable { onFavClick() },
            imageVector = if (likeState) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            tint = Purple40,
            contentDescription = null
        )
    }
}

@Composable
fun ProductDetail(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.CenterHorizontally),
            model = product.image,
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = product.title,
            textAlign = TextAlign.Center,
            color = Purple40,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = Purple80.copy(alpha = 8.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Rounded.Star, tint = Purple40, contentDescription = null)
                //Text(text = product.rate.toString(), style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "${product.price}₺", style = MaterialTheme.typography.bodyLarge)
        }
        Text(text = "Description", color = Purple40, style = MaterialTheme.typography.titleMedium)
        Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
    }
}