package com.bruno13palhano.ui.shared

interface Reducer<State: ViewState, Event: ViewEvent, SideEffect: ViewSideEffect> {
    fun reduce(previousState: State, event: Event): Pair<State, SideEffect?>
}