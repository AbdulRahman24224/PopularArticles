
package com.example.populararticles.base.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.populararticles.presentation.articles.viewmodel.SendSingleItemListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KProperty1


abstract class BaseState {
    abstract var isLoading: Boolean
     abstract var error : String
}

abstract class BaseViewModel<S : BaseState>(
    initialState: S  ,
    val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val state = MutableStateFlow(initialState)
    private val stateMutex = Mutex()

    fun currentState(): S = state.value

    val liveData: LiveData<S>
        get() = state.asLiveData()


    fun <T> Flow<T>.runAndCatch( loadingChanged: SendSingleItemListener<Boolean> , flowResult: SendSingleItemListener<T> ) {
        val flow = this
        viewModelScope.launch(defaultDispatcher) {

            loadingChanged.sendItem(true)

            flow
                .flowOn(defaultDispatcher)
                .catch { e -> launchSetState {  this.apply { error = e.localizedMessage }} }
                .collect { it ->
                    flowResult.sendItem(it)
                    loadingChanged.sendItem(false)

                }

        }
    }

    fun <A> selectObserve(prop1: KProperty1<S, A>): LiveData<A> {
        return selectSubscribe(prop1).asLiveData()
    }

    protected fun subscribe(block: (S) -> Unit) {
        viewModelScope.launch {
            state.collect { block(it) }
        }
    }

    protected fun <A> selectSubscribe(prop1: KProperty1<S, A>, block: (A) -> Unit) {
        viewModelScope.launch {
            selectSubscribe(prop1).collect { block(it) }
        }
    }

    private fun <A> selectSubscribe(prop1: KProperty1<S, A>): Flow<A> {
        return state.map { prop1.get(it) }.distinctUntilChanged()
    }

    protected suspend fun setState(reducer: S.() -> S) {

            stateMutex.withLock {
                state.value = reducer(state.value)
                Log.v("newState", state.value.toString())
            }

    }

    protected fun CoroutineScope.launchSetState(reducer: S.() -> S) {
        launch { this@BaseViewModel.setState(reducer) }
    }

    protected suspend fun withState(block: (S) -> Unit) {
        stateMutex.withLock {
            block(state.value)
        }
    }

    protected fun CoroutineScope.withState(block: (S) -> Unit) {
        launch { this@BaseViewModel.withState(block) }
    }

    override fun onCleared() {

        super.onCleared()
    }
}
