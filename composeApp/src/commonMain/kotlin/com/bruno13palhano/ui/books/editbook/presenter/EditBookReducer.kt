package com.bruno13palhano.ui.books.editbook.presenter

import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.books.shared.BookFields
import com.bruno13palhano.ui.shared.Reducer

internal class EditBookReducer : Reducer<EditBookState, EditBookEvent, EditBookSideEffect> {
    override fun reduce(
        previousState: EditBookState,
        event: EditBookEvent
    ): Pair<EditBookState, EditBookSideEffect?> {
        return when (event) {
            is EditBookEvent.LoadBook -> loadBook(previousState = previousState, book = event.book)

            is EditBookEvent.InvalidField -> {
                previousState.copy(invalidField = true) to EditBookSideEffect.InvalidField
            }

            is EditBookEvent.Done -> done(previousState = previousState)

            is EditBookEvent.Delete -> previousState to EditBookSideEffect.NavigateBack

            is EditBookEvent.NavigateBack -> previousState to EditBookSideEffect.NavigateBack
        }
    }

    private fun loadBook(
        previousState: EditBookState,
        book: Book
    ): Pair<EditBookState, EditBookSideEffect?> {
        return previousState.copy(
            bookFields = BookFields().apply { fromBook(book = book) }
        ) to null
    }

    private fun done(previousState: EditBookState): Pair<EditBookState, EditBookSideEffect> {
        return if (previousState.bookFields.isValid()) {
            previousState.copy(
                invalidField = false,
                update = true
            ) to EditBookSideEffect.NavigateBack
        } else {
            previousState.copy(
                invalidField = true,
                update = false
            ) to EditBookSideEffect.InvalidField
        }
    }
}