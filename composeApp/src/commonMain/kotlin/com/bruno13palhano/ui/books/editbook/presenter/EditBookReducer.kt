package com.bruno13palhano.ui.books.editbook.presenter

import com.bruno13palhano.ui.shared.Reducer

internal class EditBookReducer : Reducer<EditBookState, EditBookEvent, EditBookSideEffect> {
    override fun reduce(
        previousState: EditBookState,
        event: EditBookEvent
    ): Pair<EditBookState, EditBookSideEffect?> {
        return when (event) {
            is EditBookEvent.Loading -> {
                previousState.copy(loading = true) to EditBookSideEffect.Loading
            }

            is EditBookEvent.NavigateBack -> previousState to EditBookSideEffect.NavigateBack
        }
    }
}