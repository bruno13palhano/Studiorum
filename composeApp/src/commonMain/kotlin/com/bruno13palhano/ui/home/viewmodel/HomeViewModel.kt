package com.bruno13palhano.ui.home.viewmodel

import androidx.compose.runtime.Composable
import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.ui.home.presenter.HomeAction
import com.bruno13palhano.ui.home.presenter.HomeActionProcessor
import com.bruno13palhano.ui.home.presenter.HomeEvent
import com.bruno13palhano.ui.home.presenter.HomeReducer
import com.bruno13palhano.ui.home.presenter.HomeSideEffect
import com.bruno13palhano.ui.home.presenter.HomeState
import com.bruno13palhano.ui.home.presenter.homePresenter
import com.bruno13palhano.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val bookRepository: BookRepository,
) : BaseViewModel<HomeState, HomeAction, HomeEvent, HomeSideEffect>(
    actionProcessor = HomeActionProcessor(),
    reducer = HomeReducer()
) {
    @Composable
    override fun states(events: Flow<HomeEvent>): HomeState {
        return homePresenter(
            bookRepository = bookRepository,
            reducer = reducer,
            events = events,
            sendEvent = ::sendEvent,
            sendSideEffect = ::sendSideEffect
        )
    }
}