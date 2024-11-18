package com.bruno13palhano.ui.books.editbook.presenter

import androidx.compose.runtime.Immutable
import com.bruno13palhano.ui.shared.ViewAction
import com.bruno13palhano.ui.shared.ViewEvent
import com.bruno13palhano.ui.shared.ViewSideEffect
import com.bruno13palhano.ui.shared.ViewState

@Immutable
internal data class EditBookState(
    val loading: Boolean,
) : ViewState {
    companion object {
        val InitialState = EditBookState(
            loading = false,
        )
    }
}

@Immutable
internal sealed interface EditBookEvent : ViewEvent {
    data object Loading : EditBookEvent
    data object NavigateBack : EditBookEvent
}

@Immutable
internal sealed interface EditBookSideEffect : ViewSideEffect {
    data object Loading : EditBookSideEffect
    data object NavigateBack : EditBookSideEffect
}

@Immutable
internal sealed interface EditBookAction : ViewAction {
    data object OnNavigateBackClick : EditBookAction
}