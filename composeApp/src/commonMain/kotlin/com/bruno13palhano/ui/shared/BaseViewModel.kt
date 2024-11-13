package com.bruno13palhano.ui.shared

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode.ContextClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow


abstract class BaseViewModel<State: ViewState, Action: ViewAction, Event: ViewEvent, SideEffect: ViewSideEffect>(
    protected val actionProcessor: ActionProcessor<Action, Event>,
    protected val reducer: Reducer<State, Event, SideEffect>
) : ViewModel() {
    private val scope =
        CoroutineScope(viewModelScope.coroutineContext + MainClock())

    val events: MutableSharedFlow<Event> = MutableSharedFlow(extraBufferCapacity = 20)

    private val _sideEffect = Channel<SideEffect>(capacity = Channel.CONFLATED)
    val sideEffect = _sideEffect.receiveAsFlow()

    val state: StateFlow<State> by lazy(LazyThreadSafetyMode.NONE) {
        scope.launchMolecule(mode = ContextClock) {
            states(events)
        }
    }

    @Composable
    protected abstract fun states(events: Flow<Event>): State

    fun onAction(action: Action) {
        sendEvent(event = actionProcessor.process(action))
    }

    protected fun sendEvent(event: Event) {
        events.tryEmit(event)
    }

    fun sendSideEffect(sideEffect: SideEffect) {
        _sideEffect.trySend(sideEffect)
    }
}