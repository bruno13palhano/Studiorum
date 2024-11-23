package com.bruno13palhano.ui.books.newbook.presenter

import androidx.compose.runtime.Immutable
import com.bruno13palhano.ui.shared.ViewAction
import com.bruno13palhano.ui.shared.ViewEvent
import com.bruno13palhano.ui.shared.ViewSideEffect
import com.bruno13palhano.ui.shared.ViewState

@Immutable
internal data class NewBookState(
    val loading: Boolean,
    val invalidField: Boolean,
    val insert: Boolean,
    val bookFields: NewBookFields
) : ViewState {
    companion object {
        val InitialState = NewBookState(
            loading = false,
            invalidField = false,
            insert = false,
            bookFields = NewBookFields()
        )
    }
}

@Immutable
internal sealed interface NewBookEvent : ViewEvent {
    data object Loading : NewBookEvent
    data object InvalidField : NewBookEvent
    data object Done : NewBookEvent
    data object NavigateBack : NewBookEvent
}

@Immutable
internal sealed interface NewBookSideEffect : ViewSideEffect {
    data object Loading : NewBookSideEffect
    data object InvalidField : NewBookSideEffect
    data object NavigateBack : NewBookSideEffect
}

@Immutable
internal sealed interface NewBookAction : ViewAction {
    data object OnDoneClick : NewBookAction
    data object OnNavigateBackClick : NewBookAction
}