package com.bruno13palhano.ui.books.editbook.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.ui.books.shared.BookFields
import com.bruno13palhano.ui.shared.Reducer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@Composable
internal fun editBookPresenter(
    bookRepository: BookRepository,
    reducer: Reducer<EditBookState, EditBookEvent, EditBookSideEffect>,
    events: Flow<EditBookEvent>,
    sendEvent: (event: EditBookEvent) -> Unit,
    sendSideEffect: (sideEffect: EditBookSideEffect) -> Unit
): EditBookState {
    val state = remember { mutableStateOf(EditBookState.InitialState) }

    HandleEvents(
        events = events,
        state = state,
        reducer = reducer,
        sendSideEffect = sendSideEffect
    )

    GetBook(
        id = state.value.bookFields.id,
        bookRepository = bookRepository,
        sendEvent = sendEvent
    )

    UpdateBook(
        update = state.value.update,
        bookRepository = bookRepository,
        bookFields = state.value.bookFields
    )

    return state.value
}

@Composable
private fun HandleEvents(
    events: Flow<EditBookEvent>,
    state: MutableState<EditBookState>,
    reducer: Reducer<EditBookState, EditBookEvent, EditBookSideEffect>,
    sendSideEffect: (sideEffect: EditBookSideEffect) -> Unit
) {
    LaunchedEffect(Unit) {
        events.collect { event ->
            reducer.reduce(previousState = state.value, event = event).let {
                state.value = it.first
                it.second?.let(sendSideEffect)
            }
        }
    }
}

@Composable
private fun GetBook(
    id: Long,
    bookRepository: BookRepository,
    sendEvent: (event: EditBookEvent) -> Unit
) {
    LaunchedEffect(id) {
        if (id == 0L) return@LaunchedEffect

        bookRepository.getById(id = id)
            .catch { it.printStackTrace() }
            .collect { book ->
                book?.let {
                    sendEvent(EditBookEvent.LoadBook(book = book))
                }
            }
    }
}

@Composable
private fun UpdateBook(
    update: Boolean,
    bookRepository: BookRepository,
    bookFields: BookFields
) {
    LaunchedEffect(update) {
        if (update) {
            bookRepository.update(book = bookFields.toBook(id = bookFields.id))
        }
    }
}