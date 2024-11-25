package com.bruno13palhano.ui.books.editbook.presenter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bruno13palhano.ui.books.editbook.viewmodel.EditBookViewModel
import com.bruno13palhano.ui.books.shared.BookContent
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun EditBookRoute(
    modifier: Modifier = Modifier,
    id: Long,
    navigateBack: () -> Unit,
    viewModel: EditBookViewModel = koinViewModel<EditBookViewModel>()
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(action = EditBookAction.OnLoadBook(id = id))
    }

    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(sideEffect) {
        sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is EditBookSideEffect.InvalidField -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Some message",
                            withDismissAction = true
                        )
                    }
                }

                is EditBookSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    EditBookContent(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditBookContent(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    state: EditBookState,
    onAction: (action: EditBookAction) -> Unit
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = state.bookFields.title) },
                navigationIcon = {
                    IconButton(onClick = { onAction(EditBookAction.OnNavigateBackClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onAction(EditBookAction.OnDeleteClick) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(EditBookAction.OnDoneClick) }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            }
        }
    ) {
        BookContent(
            modifier = Modifier.padding(it),
            title = state.bookFields.title,
            author = state.bookFields.author,
            pages = state.bookFields.pages,
            updateTitleChange = state.bookFields::updateTitleChange,
            updateAuthorChange = state.bookFields::updateAuthorChange,
            updatePagesChange = state.bookFields::updatePagesChange
        )
    }
}