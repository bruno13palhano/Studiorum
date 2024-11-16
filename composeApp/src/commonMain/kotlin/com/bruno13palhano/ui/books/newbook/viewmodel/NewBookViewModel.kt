package com.bruno13palhano.ui.books.newbook.viewmodel

import androidx.compose.runtime.Composable
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.ui.books.newbook.presenter.NewBookAction
import com.bruno13palhano.ui.books.newbook.presenter.NewBookActionProcessor
import com.bruno13palhano.ui.books.newbook.presenter.NewBookEvent
import com.bruno13palhano.ui.books.newbook.presenter.NewBookReducer
import com.bruno13palhano.ui.books.newbook.presenter.NewBookSideEffect
import com.bruno13palhano.ui.books.newbook.presenter.NewBookState
import com.bruno13palhano.ui.books.newbook.presenter.newBookPresenter
import com.bruno13palhano.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow

internal class NewBookViewModel(
    private val bookRepository: BookRepository
) : BaseViewModel<NewBookState, NewBookAction, NewBookEvent, NewBookSideEffect>(
    actionProcessor = NewBookActionProcessor(),
    reducer = NewBookReducer()
) {
    @Composable
    override fun states(events: Flow<NewBookEvent>): NewBookState {
        return newBookPresenter(
            bookRepository = bookRepository,
            reducer = reducer,
            events = events,
            sendEvent = ::sendEvent,
            sendSideEffect = ::sendSideEffect
        )
    }
}