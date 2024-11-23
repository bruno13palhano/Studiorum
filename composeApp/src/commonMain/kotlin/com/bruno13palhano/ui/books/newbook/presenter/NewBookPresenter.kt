package com.bruno13palhano.ui.books.newbook.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.ui.shared.Reducer
import kotlinx.coroutines.flow.Flow

@Composable
internal fun newBookPresenter(
    bookRepository: BookRepository,
    reducer: Reducer<NewBookState, NewBookEvent, NewBookSideEffect>,
    events: Flow<NewBookEvent>,
    sendSideEffect: (sideEffect: NewBookSideEffect) -> Unit
): NewBookState {
    val state = remember { mutableStateOf(NewBookState.InitialState) }

    HandleEvents(
        events = events,
        state = state,
        reducer = reducer,
        sendSideEffect = sendSideEffect
    )

    Insert(
        insert = state.value.insert,
        bookRepository = bookRepository,
        newBookFields = state.value.bookFields
    )

    return state.value
}

@Composable
private fun HandleEvents(
    events: Flow<NewBookEvent>,
    state: MutableState<NewBookState>,
    reducer: Reducer<NewBookState, NewBookEvent, NewBookSideEffect>,
    sendSideEffect: (sideEffect: NewBookSideEffect) -> Unit
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
private fun Insert(
    insert: Boolean,
    bookRepository: BookRepository,
    newBookFields: NewBookFields
) {
    LaunchedEffect(insert) {
        if (insert) {
            bookRepository.insert(book = newBookFields.toBook())
        }
    }
}