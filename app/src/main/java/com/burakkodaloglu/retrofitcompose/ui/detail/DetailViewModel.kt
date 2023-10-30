package com.burakkodaloglu.retrofitcompose.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.burakkodaloglu.retrofitcompose.common.Resource
import com.burakkodaloglu.retrofitcompose.data.model.Product
import com.burakkodaloglu.retrofitcompose.domain.repository.ProductRepository
import com.burakkodaloglu.retrofitcompose.ui.base.BaseViewModel
import com.burakkodaloglu.retrofitcompose.ui.base.Effect
import com.burakkodaloglu.retrofitcompose.ui.base.Event
import com.burakkodaloglu.retrofitcompose.ui.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailState, DetailEffect, DetailEvent>() {
    override fun setInitialState() = DetailState(true)

    override fun handleEvents(event: DetailEvent) {
        when (event) {
            DetailEvent.FavClicked -> {
                setState(getCurrentState().copy(favState = getCurrentState().favState))
            }
        }
    }

    init {
        getProductById(savedStateHandle.get<String>("productId")?.toInt() ?: 1)
    }

    private fun getProductById(productId: Int) {
        viewModelScope.launch {
            when (val result = productRepository.getProductId(productId)) {
                is Resource.Success -> {
                    setState(DetailState(false, false, product = result.data))
                }

                is Resource.Error -> {
                    setEffect(DetailEffect.ShowError(result.message))
                }
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val favState: Boolean = false,
    val product: Product? = null
) : State

sealed interface DetailEffect : Effect {
    data class ShowError(val message: String) : DetailEffect
}

sealed interface DetailEvent : Event {
    object FavClicked : DetailEvent
}