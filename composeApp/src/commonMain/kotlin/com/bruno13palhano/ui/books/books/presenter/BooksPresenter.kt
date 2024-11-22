package com.bruno13palhano.ui.books.books.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.shared.Reducer
import kotlinx.coroutines.flow.Flow

@Composable
internal fun booksPresenter(
    bookRepository: BookRepository,
    reducer: Reducer<BooksState, BooksEvent, BooksSideEffect>,
    events: Flow<BooksEvent>,
    sendEvent: (event: BooksEvent) -> Unit,
    sendSideEffect: (sideEffect: BooksSideEffect) -> Unit
): BooksState {
    val state = remember { mutableStateOf<BooksState>(BooksState.Loading) }

    HandleEvents(
        events = events,
        state = state,
        reducer = reducer,
        sendSideEffect = sendSideEffect
    )

    GetBooks(books = bookRepository.getAll(), sendEvent = sendEvent)

    return state.value
}

@Composable
private fun HandleEvents(
    events: Flow<BooksEvent>,
    state: MutableState<BooksState>,
    reducer: Reducer<BooksState, BooksEvent, BooksSideEffect>,
    sendSideEffect: (sideEffect: BooksSideEffect) -> Unit
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
private fun GetBooks(
    books: Flow<List<Book>>,
    sendEvent: (event: BooksEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        books.collect {
            sendEvent(BooksEvent.UpdateBooks(books = it))
        }
    }
}