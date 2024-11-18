package com.bruno13palhano.ui.books.editbook.presenter

import com.bruno13palhano.ui.shared.ActionProcessor

internal class EditBookActionProcessor : ActionProcessor<EditBookAction, EditBookEvent> {
    override fun process(viewAction: EditBookAction): EditBookEvent {
        return when (viewAction) {
            is EditBookAction.OnNavigateBackClick -> EditBookEvent.NavigateBack
        }
    }
}