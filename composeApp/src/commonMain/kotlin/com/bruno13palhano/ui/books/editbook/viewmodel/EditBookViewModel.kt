package com.bruno13palhano.ui.books.editbook.viewmodel

import androidx.compose.runtime.Composable
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.ui.books.editbook.presenter.EditBookAction
import com.bruno13palhano.ui.books.editbook.presenter.EditBookActionProcessor
import com.bruno13palhano.ui.books.editbook.presenter.EditBookEvent
import com.bruno13palhano.ui.books.editbook.presenter.EditBookReducer
import com.bruno13palhano.ui.books.editbook.presenter.EditBookSideEffect
import com.bruno13palhano.ui.books.editbook.presenter.EditBookState
import com.bruno13palhano.ui.books.editbook.presenter.editBookPresenter
import com.bruno13palhano.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow

internal class EditBookViewModel(
    private val bookRepository: BookRepository
) : BaseViewModel<EditBookState, EditBookAction,EditBookEvent, EditBookSideEffect>(
    actionProcessor = EditBookActionProcessor(),
    reducer = EditBookReducer()
){
    @Composable
    override fun states(events: Flow<EditBookEvent>): EditBookState {
        return editBookPresenter(
            bookRepository = bookRepository,
            reducer = reducer,
            events = events,
            sendEvent = ::sendEvent,
            sendSideEffect = ::sendSideEffect
        )
    }
}