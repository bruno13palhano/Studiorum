package com.bruno13palhano.ui.books.books.presenter

import androidx.compose.runtime.Immutable
import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.shared.ViewAction
import com.bruno13palhano.ui.shared.ViewEvent
import com.bruno13palhano.ui.shared.ViewSideEffect
import com.bruno13palhano.ui.shared.ViewState

@Immutable
internal sealed interface BooksState : ViewState {
    data object Loading : BooksState
    data class Books(val books: List<Book>) : BooksState
}

@Immutable
internal sealed interface BooksEvent : ViewEvent {
    data object Loading : BooksEvent
    data class UpdateBooks(val books: List<Book>) : BooksEvent
    data object NewBook : BooksEvent
    data class EditBook(val id: Long) : BooksEvent
    data object OpenDrawerMenu : BooksEvent
}

@Immutable
internal sealed interface BooksSideEffect : ViewSideEffect {
    data object NavigateToNewBook : BooksSideEffect
    data class NavigateToEditBook(val id: Long) : BooksSideEffect
    data object OpenDrawerMenu : BooksSideEffect
}

@Immutable
internal sealed interface BooksAction : ViewAction {
    data object OnNewBookClick : BooksAction
    data class OnBookClick(val id: Long) : BooksAction
    data object OnIconMenuClick : BooksAction
}