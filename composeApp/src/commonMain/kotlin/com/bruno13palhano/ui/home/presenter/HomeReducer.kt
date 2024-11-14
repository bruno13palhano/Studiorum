package com.bruno13palhano.ui.home.presenter

import com.bruno13palhano.ui.shared.Reducer

class HomeReducer : Reducer<HomeState, HomeEvent, HomeSideEffect> {
    override fun reduce(
        previousState: HomeState,
        event: HomeEvent
    ): Pair<HomeState, HomeSideEffect?> {
        return when (event) {
            is HomeEvent.Loading -> previousState.copy(loading = true) to HomeSideEffect.Loading

            is HomeEvent.UpdateBooks -> {
                previousState.copy(loading = false, books = event.books) to null
            }
        }
    }
}