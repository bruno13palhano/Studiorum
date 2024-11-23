package com.bruno13palhano.ui.books.newbook.presenter

import com.bruno13palhano.ui.shared.ActionProcessor

internal class NewBookActionProcessor : ActionProcessor<NewBookAction, NewBookEvent> {
    override fun process(viewAction: NewBookAction): NewBookEvent {
        return when (viewAction) {
            is NewBookAction.OnDoneClick -> NewBookEvent.Done

            is NewBookAction.OnNavigateBackClick -> NewBookEvent.NavigateBack
        }
    }
}