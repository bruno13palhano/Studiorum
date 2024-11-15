package com.bruno13palhano.ui.books.books.viewmodel

import androidx.compose.runtime.Composable
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.ui.books.books.presenter.BooksAction
import com.bruno13palhano.ui.books.books.presenter.BooksActionProcessor
import com.bruno13palhano.ui.books.books.presenter.BooksEvent
import com.bruno13palhano.ui.books.books.presenter.BooksReducer
import com.bruno13palhano.ui.books.books.presenter.BooksSideEffect
import com.bruno13palhano.ui.books.books.presenter.BooksState
import com.bruno13palhano.ui.books.books.presenter.booksPresenter
import com.bruno13palhano.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow

internal class BooksViewModel(
    private val bookRepository: BookRepository
) : BaseViewModel<BooksState, BooksAction, BooksEvent, BooksSideEffect>(
    actionProcessor = BooksActionProcessor(),
    reducer = BooksReducer()
) {
    @Composable
    override fun states(events: Flow<BooksEvent>): BooksState {
        return booksPresenter(
            bookRepository = bookRepository,
            reducer = reducer,
            events = events,
            sendEvent = ::sendEvent,
            sendSideEffect = ::sendSideEffect
        )
    }
}