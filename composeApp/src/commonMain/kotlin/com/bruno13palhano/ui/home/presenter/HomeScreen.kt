package com.bruno13palhano.ui.home.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bruno13palhano.ui.home.viewmodel.HomeViewModel
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.app_name

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    openDrawerMenu: () -> Unit,
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.Loading -> {

                }

                is HomeSideEffect.OpenDrawerMenu -> openDrawerMenu()
            }
        }
    }

    HomeContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onAction: (action: HomeAction) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(HomeAction.OnIconMenuClick) }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}