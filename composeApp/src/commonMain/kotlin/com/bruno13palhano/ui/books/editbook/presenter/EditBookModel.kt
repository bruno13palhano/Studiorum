package com.bruno13palhano.ui.books.editbook.presenter

import androidx.compose.runtime.Immutable
import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.books.shared.BookFields
import com.bruno13palhano.ui.shared.ViewAction
import com.bruno13palhano.ui.shared.ViewEvent
import com.bruno13palhano.ui.shared.ViewSideEffect
import com.bruno13palhano.ui.shared.ViewState

@Immutable
internal data class EditBookState(
    val invalidField: Boolean,
    val update: Boolean,
    val bookFields: BookFields
) : ViewState {
    companion object {
        val InitialState = EditBookState(
            invalidField = false,
            update = false,
            bookFields = BookFields()
        )
    }
}

@Immutable
internal sealed interface EditBookEvent : ViewEvent {
    data class LoadBook(val book: Book) : EditBookEvent
    data object InvalidField : EditBookEvent
    data object Done : EditBookEvent
    data object NavigateBack : EditBookEvent
}

@Immutable
internal sealed interface EditBookSideEffect : ViewSideEffect {
    data object InvalidField : EditBookSideEffect
    data object NavigateBack : EditBookSideEffect
}

@Immutable
internal sealed interface EditBookAction : ViewAction {
    data class OnLoadBook(val id: Long) : EditBookAction
    data object OnDoneClick : EditBookAction
    data object OnNavigateBackClick : EditBookAction
}