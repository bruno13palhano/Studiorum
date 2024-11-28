package com.bruno13palhano.ui.home.presenter

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
fun homePresenter(
    bookRepository: BookRepository,
    reducer: Reducer<HomeState, HomeEvent, HomeSideEffect>,
    events: Flow<HomeEvent>,
    sendEvent: (event: HomeEvent) -> Unit,
    sendSideEffect: (sideEffect: HomeSideEffect) -> Unit
): HomeState {
    val state = remember { mutableStateOf(HomeState.InitialState) }

    handleEvents(
        events = events,
        state = state,
        reducer = reducer,
        sendSideEffect = sendSideEffect
    )

    GetBooks(books = bookRepository.getAll(), sendEvent = sendEvent)

    return state.value
}

@Composable
private fun handleEvents(
    events: Flow<HomeEvent>,
    state: MutableState<HomeState>,
    reducer: Reducer<HomeState, HomeEvent, HomeSideEffect>,
    sendSideEffect: (sideEffect: HomeSideEffect) -> Unit
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
    sendEvent: (event: HomeEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        books.collect {
            sendEvent(HomeEvent.UpdateBooks(books = it))
        }
    }
}