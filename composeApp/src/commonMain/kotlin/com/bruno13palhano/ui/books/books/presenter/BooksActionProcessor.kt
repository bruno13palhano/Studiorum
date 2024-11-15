package com.bruno13palhano.ui.books.books.presenter

import com.bruno13palhano.ui.shared.ActionProcessor

internal class BooksActionProcessor : ActionProcessor<BooksAction, BooksEvent> {
    override fun process(viewAction: BooksAction): BooksEvent {
        return when (viewAction) {
            is BooksAction.OnNewBookClick -> BooksEvent.NewBook

            is BooksAction.OnBookClick -> BooksEvent.EditBook(viewAction.id)
        }
    }
}