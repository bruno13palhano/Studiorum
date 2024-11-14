package com.bruno13palhano.ui.home.presenter

import androidx.compose.runtime.Immutable
import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.shared.ViewAction
import com.bruno13palhano.ui.shared.ViewEvent
import com.bruno13palhano.ui.shared.ViewSideEffect
import com.bruno13palhano.ui.shared.ViewState

@Immutable
data class HomeState(
    val loading: Boolean,
    val books: List<Book>
) : ViewState {
    companion object {
        val initialState = HomeState(
            loading = false,
            books = emptyList()
        )
    }
}

@Immutable
sealed interface HomeEvent : ViewEvent {
    data object Loading : HomeEvent
    data class UpdateBooks(val books: List<Book>) : HomeEvent
}

@Immutable
sealed interface HomeSideEffect : ViewSideEffect {
    data object Loading : HomeSideEffect
}

@Immutable
sealed interface HomeAction : ViewAction {

}