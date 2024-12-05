package com.bruno13palhano.ui.books.newbook.presenter

import androidx.compose.runtime.Immutable
import com.bruno13palhano.ui.books.shared.BookFields
import com.bruno13palhano.ui.shared.ViewAction
import com.bruno13palhano.ui.shared.ViewEvent
import com.bruno13palhano.ui.shared.ViewSideEffect
import com.bruno13palhano.ui.shared.ViewState

@Immutable
internal data class NewBookState(
    val invalidField: Boolean,
    val insert: Boolean,
    val categoryVisible: Boolean,
    val bookFields: BookFields
) : ViewState {
    companion object {
        val InitialState = NewBookState(
            invalidField = false,
            insert = false,
            categoryVisible = false,
            bookFields = BookFields()
        )
    }
}

@Immutable
internal sealed interface NewBookEvent : ViewEvent {
    data object InvalidField : NewBookEvent
    data object Done : NewBookEvent
    data class UpdateCategoryVisibility(val visible: Boolean) : NewBookEvent
    data object NavigateBack : NewBookEvent
}

@Immutable
internal sealed interface NewBookSideEffect : ViewSideEffect {
    data object InvalidField : NewBookSideEffect
    data object NavigateBack : NewBookSideEffect
}

@Immutable
internal sealed interface NewBookAction : ViewAction {
    data object OnDoneClick : NewBookAction
    data class OnCategoryVisibilityClick(val visible: Boolean) : NewBookAction
    data object OnNavigateBackClick : NewBookAction
}