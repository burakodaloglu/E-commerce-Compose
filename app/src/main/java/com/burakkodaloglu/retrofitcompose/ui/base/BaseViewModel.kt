package com.burakkodaloglu.retrofitcompose.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
Bunları neden yapıyoruz?
Neden burada içi boş interfaceler var?
Because - Çünkü,
Biz Product Screen ile alakalı olan State, Effect, Event gibi durumları
Detail Screende yeniden kullanacağız kod fazlalığı olmasın generic bir
yapıda olsun diye bunları tek bir çatıya topluyoruz.
BaseViewModel<STATE : State, EFFECT : Effect, EVENT : Event>
Burada orn:State interface i tipinde bir class gelecek diyoruz ve BaseViewModel i herhangi
bir yerde kullandığımızda State, Effect ve Event inteface lerinden kalıtım alınmış oluyor.
 */
abstract class BaseViewModel<STATE : State, EFFECT : Effect, EVENT : Event> : ViewModel() {

    private val initalState: STATE by lazy { setInitialState() }

    abstract fun setInitialState(): STATE


    //StateFlowlar initial değer bekler. Stateler sürekli bir akış halindeki veriler için kullanılıyor.
    //Bu LiveData gibi initial bir değer ile dinlemeye başlayan yapıdadır.
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initalState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    //SharedFlowlar bir event sonucu Bir sayfaya gelip bir butona tıklayıp servisi çağıracaksak bunu kullanıyoruz.
    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    private val _event: MutableSharedFlow<EVENT> = MutableSharedFlow()
    val event: SharedFlow<EVENT> = _event.asSharedFlow()

    fun getCurrentState() = state.value

    init {
        subscribeToEvents()
    }

    abstract fun handleEvents(event: EVENT)

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setState(state: STATE) {
        viewModelScope.launch { _state.emit(state) }
    }

    fun setEffect(effect: EFFECT) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    fun setEvent(event: EVENT) {
        viewModelScope.launch { _event.emit(event) }
    }
}


interface State
interface Effect
interface Event