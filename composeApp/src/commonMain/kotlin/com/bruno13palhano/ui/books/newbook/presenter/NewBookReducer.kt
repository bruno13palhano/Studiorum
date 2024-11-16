package com.bruno13palhano.ui.books.newbook.presenter

import com.bruno13palhano.ui.shared.Reducer

internal class NewBookReducer : Reducer<NewBookState, NewBookEvent, NewBookSideEffect>{
    override fun reduce(
        previousState: NewBookState,
        event: NewBookEvent
    ): Pair<NewBookState, NewBookSideEffect?> {
        return when (event) {
            is NewBookEvent.Loading -> {
                previousState.copy(loading = true) to null
            }

            is NewBookEvent.NavigateBack -> {
                previousState.copy(loading = false) to NewBookSideEffect.NavigateBack
            }
        }
    }
}