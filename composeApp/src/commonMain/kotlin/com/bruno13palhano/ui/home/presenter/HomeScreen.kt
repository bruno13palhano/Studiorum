package com.bruno13palhano.ui.home.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.bruno13palhano.ui.home.viewmodel.HomeViewModel
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.app_name

@Composable
fun HomeRoute(
    navigateToNewBook: () -> Unit,
    navigateToEditBook: (id: Long) -> Unit,
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.Loading -> {

                }
            }
        }
    }

    HomeContent(state = state, navigateToNewBook = navigateToNewBook)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    state: HomeState,
    navigateToNewBook: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(Res.string.app_name)) })
        }
    ) {
//        LazyColumn(modifier = Modifier.padding(it)) {
//            items(items = state.books, key = { book -> book.id }) { book ->
//                Text(text = book.title)
//            }
//        }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(modifier = Modifier.padding(it)) {
            TextField(
                value = "",
                onValueChange = {}
            )
            Button(
                onClick = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()

                    navigateToNewBook()
                }
            ) {
                Text("Click me!")
            }
        }
    }
}