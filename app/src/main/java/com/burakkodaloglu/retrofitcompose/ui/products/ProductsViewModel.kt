package com.burakkodaloglu.retrofitcompose.ui.products

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
class ProductsViewModel @Inject constructor(private val productRepository: ProductRepository) :
    BaseViewModel<ProductUiState, ProductEffect, ProductEvent>() {

    //Sayfa açılınca isLoading true olacak.
    override fun setInitialState(): ProductUiState = ProductUiState(true)

    override fun handleEvents(event: ProductEvent) {}

    init {
        getProduct()
    }

    fun getProduct() = viewModelScope.launch {
        when (val result = productRepository.getProduct()) {
            is Resource.Success -> {
                setState(ProductUiState(false, result.data))
                /*setState -> BaseViewModeldeki fonksiyonumuz bu olmasayı oraya yazdığımız kodları burada yazacaktık
                Şimdi sen diyorsun ki ne olacak yani ha buraya yazmışız ha oraya Yazılımda Mimariler, prensipler(SOLID) vb...
                 kodu daha okunabilir, daha işlevsel ve temiz olmasını sağlamak için bulunmuştur.
                 Bu yüden daha temiz, okunabilir hale gelmesi için bunları yapıyoruz.

                 S — Single-responsibility principle
                 Bir sınıf (nesne) yalnızca bir amaç uğruna değiştirilebilir,
                 o da o sınıfa yüklenen sorumluluktur, yani bir sınıfın(fonksiyona da indirgenebilir)
                 yapması gereken yalnızca bir işi olması gerekir.

                O — Open-closed principle
                Bir sınıf ya da fonksiyon halihazırda var olan özellikleri korumalı ve değişikliğe izin vermemelidir.
                Yani davranışını değiştirmiyor olmalı ve yeni özellikler kazanabiliyor olmalıdır.

                L — Liskov substitution principle
                Kodlarımızda herhangi bir değişiklik yapmaya gerek duymadan alt sınıfları, türedikleri(üst)
                sınıfların yerine kullanabilmeliyiz.

                I — Interface segregation principle
                Sorumlulukların hepsini tek bir arayüze toplamak yerine daha
                özelleştirilmiş birden fazla arayüz oluşturmalıyız.

                D — Dependency Inversion Principle
                Sınıflar arası bağımlılıklar olabildiğince az olmalıdır
                özellikle üst seviye sınıflar alt seviye sınıflara bağımlı olmamalıdır.
                 */
            }

            is Resource.Error -> {
                setState(ProductUiState(false))
                setEffect(ProductEffect.ShowError(result.message))
            }
        }
    }
}

data class ProductUiState(
    //ekran geldiği anda bir progressBar dönmeye başlayacak.
    val isLoading: Boolean = false,
    //Servisten gelen sonuç başarılı ise bu liste dolacak ve ekranın durumu belli olacak
    val product: List<Product> = emptyList()
) : State

sealed interface ProductEffect : Effect {
    //Servisten başarısız bir sonuç gelirse Effect çalışacak ve Error Screen açılacak
    data class ShowError(val message: String) : ProductEffect
}

sealed interface ProductEvent : Event {

}