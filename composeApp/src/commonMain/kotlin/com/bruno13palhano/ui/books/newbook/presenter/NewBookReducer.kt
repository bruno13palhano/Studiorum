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

            is NewBookEvent.InvalidField -> {
                previousState.copy(
                    loading = false,
                    invalidField = true
                ) to NewBookSideEffect.InvalidField
            }

            is NewBookEvent.Done -> done(previousState = previousState)

            is NewBookEvent.NavigateBack -> {
                previousState.copy(loading = false) to NewBookSideEffect.NavigateBack
            }
        }
    }

    private fun done(previousState: NewBookState): Pair<NewBookState, NewBookSideEffect> {
        return if (previousState.bookFields.isValid()) {
            previousState.copy(
                invalidField = false,
                insert = true
            ) to NewBookSideEffect.NavigateBack
        } else {
            previousState.copy(
                invalidField = true,
                insert = false
            ) to NewBookSideEffect.InvalidField
        }
    }
}