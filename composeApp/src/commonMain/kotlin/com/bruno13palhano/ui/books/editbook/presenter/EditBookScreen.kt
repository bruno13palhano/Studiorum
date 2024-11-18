package com.bruno13palhano.ui.books.editbook.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bruno13palhano.ui.books.editbook.viewmodel.EditBookViewModel
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.edit_book_title

@Composable
internal fun EditBookRoute(
    modifier: Modifier = Modifier,
    id: Long,
    navigateBack: () -> Unit,
    viewModel: EditBookViewModel = koinViewModel<EditBookViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    LaunchedEffect(sideEffect) {
        sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is EditBookSideEffect.Loading -> {}

                is EditBookSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    EditBookContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditBookContent(
    modifier: Modifier = Modifier,
    state: EditBookState,
    onAction: (action: EditBookAction) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.edit_book_title, "Book title")
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(EditBookAction.OnNavigateBackClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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