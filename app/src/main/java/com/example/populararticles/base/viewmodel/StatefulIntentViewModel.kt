package com.example.populararticles.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


abstract class IntentViewModel<Intent> : ViewModel() {
    private val intents = Channel<Intent>()
    fun send(intent: Intent) = viewModelScope.launch { intents.send(intent) }
    protected abstract suspend fun handleIntent(intent: Intent)
    init {
        viewModelScope.launch {
            intents.consumeEach { intent ->
                handleIntent(intent)
            }
        }
    }
}
abstract class StatefulIntentViewModel<Intent, State>(
    val initialState: State
) : IntentViewModel<Intent>() {
    private var state = initialState
    private val statesBroadcast = BroadcastChannel<State>(1)
    private val stateMutex = Mutex()
    val states = statesBroadcast.asFlow()
    protected suspend fun setState(reducer: State.() -> State) = 
        stateMutex.withLock {
            state = state.reducer()
            statesBroadcast.send(state)
        }
    protected suspend fun withState(action: (State).() -> Unit) = 
        setState {
            this.apply(action)
        }
}