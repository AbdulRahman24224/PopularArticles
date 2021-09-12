
package com.example.populararticles.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KProperty1


abstract class BaseState {
    abstract var isLoading: Boolean
     abstract var error : String
}

class SendSingleItemListener<T>(val item: (item: T) -> Unit) {
    fun sendItem(item: T) = item(item)
}

abstract class BaseViewModel<S : BaseState>(
    initialState: S  ,
    val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    private val stateMutex = Mutex()

    val  state: S
    get() = _state.value

     val  statesList: MutableList<S> = mutableListOf()


    val liveData: LiveData<S>
        get() = _state.asLiveData()


    fun <T> Flow<T>.runAndCatch( loadingChanged: SendSingleItemListener<Boolean> , flowResult: SendSingleItemListener<T> ) {
        val flow = this
        viewModelScope.launch(defaultDispatcher) {

            loadingChanged.sendItem(true)

            flow
                .flowOn(defaultDispatcher)
                .catch { e -> setState {  this.apply { error = e.message?:"Exception Error" }} }
                .collect { it ->
                    flowResult.sendItem(it)
                    loadingChanged.sendItem(false)

                }

        }
    }

    protected fun subscribe(block: (S) -> Unit) {
        viewModelScope.launch {
            _state.collect { block(it) }
        }
    }

    protected fun <A> selectSubscribe(prop1: KProperty1<S, A>, block: (A) -> Unit) {
        viewModelScope.launch {
            selectSubscribe(prop1).collect { block(it) }
        }
    }

    private fun <A> selectSubscribe(prop1: KProperty1<S, A>): Flow<A> {
        return _state.map { prop1.get(it) }.distinctUntilChanged()
    }

    protected suspend fun setState(reducer: S.() -> S) {

            stateMutex.withLock {
                _state.value = reducer(_state.value)
            //    Log.v("newState", state.value.toString())
                statesList.add(_state.value)
            }

    }


    override fun onCleared() {

        super.onCleared()
    }
}
