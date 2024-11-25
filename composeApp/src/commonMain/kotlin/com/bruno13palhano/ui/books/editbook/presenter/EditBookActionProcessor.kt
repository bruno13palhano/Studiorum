package com.bruno13palhano.ui.books.editbook.presenter

import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.shared.ActionProcessor

internal class EditBookActionProcessor : ActionProcessor<EditBookAction, EditBookEvent> {
    override fun process(viewAction: EditBookAction): EditBookEvent {
        return when (viewAction) {
            is EditBookAction.OnLoadBook -> {
                EditBookEvent.LoadBook(book = Book.EMPTY.copy(id = viewAction.id))
            }

            is EditBookAction.OnDoneClick -> EditBookEvent.Done

            is EditBookAction.OnDeleteClick -> EditBookEvent.Delete

            is EditBookAction.OnNavigateBackClick -> EditBookEvent.NavigateBack
        }
    }
}