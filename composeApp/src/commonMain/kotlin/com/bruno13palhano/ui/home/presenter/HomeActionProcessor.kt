package com.bruno13palhano.ui.home.presenter

import com.bruno13palhano.ui.shared.ActionProcessor

class HomeActionProcessor : ActionProcessor<HomeAction, HomeEvent> {
    override fun process(viewAction: HomeAction): HomeEvent {
        return when (viewAction) {
            is HomeAction.OnIconMenuClick -> HomeEvent.OpenDrawerMenu
        }
    }
}