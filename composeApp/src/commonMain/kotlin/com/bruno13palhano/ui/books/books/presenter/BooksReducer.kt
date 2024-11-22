package com.bruno13palhano.ui.books.books.presenter

import com.bruno13palhano.ui.shared.Reducer

internal class BooksReducer : Reducer<BooksState, BooksEvent, BooksSideEffect> {
    override fun reduce(
        previousState: BooksState,
        event: BooksEvent
    ): Pair<BooksState, BooksSideEffect?> {
        return when (event) {
            is BooksEvent.Loading -> BooksState.Loading to null

            is BooksEvent.UpdateBooks -> BooksState.Books(books = event.books) to null

            is BooksEvent.NewBook -> previousState to BooksSideEffect.NavigateToNewBook

            is BooksEvent.EditBook -> {
                previousState to BooksSideEffect.NavigateToEditBook(id = event.id)
            }

            is BooksEvent.OpenDrawerMenu -> previousState to BooksSideEffect.OpenDrawerMenu
        }
    }
}