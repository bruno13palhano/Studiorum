package com.bruno13palhano.ui.books.books.presenter

import com.bruno13palhano.ui.shared.Reducer

internal class BooksReducer : Reducer<BooksState, BooksEvent, BooksSideEffect> {
    override fun reduce(
        previousState: BooksState,
        event: BooksEvent
    ): Pair<BooksState, BooksSideEffect?> {
        return when (event) {
            is BooksEvent.Loading -> previousState.copy(loading = true) to BooksSideEffect.Loading

            is BooksEvent.UpdateBooks -> previousState.copy(
                loading = false,
                books = event.books
            ) to null

            is BooksEvent.NewBook -> previousState.copy() to BooksSideEffect.NavigateToNewBook

            is BooksEvent.EditBook -> {
                previousState.copy() to BooksSideEffect.NavigateToEditBook(id = event.id)
            }
        }
    }
}