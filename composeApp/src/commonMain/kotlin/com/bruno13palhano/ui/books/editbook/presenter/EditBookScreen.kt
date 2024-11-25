package com.bruno13palhano.ui.books.editbook.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bruno13palhano.ui.books.editbook.viewmodel.EditBookViewModel
import com.bruno13palhano.ui.components.CustomIntegerField
import com.bruno13palhano.ui.components.CustomTextField
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.author
import studiorum.composeapp.generated.resources.author_placeholder
import studiorum.composeapp.generated.resources.pages
import studiorum.composeapp.generated.resources.pages_placeholder
import studiorum.composeapp.generated.resources.title
import studiorum.composeapp.generated.resources.title_placeholder

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

    LaunchedEffect(sideEffect) {
        sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is EditBookSideEffect.InvalidField -> {}

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
                        text = state.bookFields.title
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
        Column(modifier = Modifier.padding(it)) {
            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                value = state.bookFields.title,
                onValueChange = state.bookFields::updateTitleChange,
                label = stringResource(Res.string.title),
                placeholder = stringResource(Res.string.title_placeholder)
            )

            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                value = state.bookFields.author,
                onValueChange = state.bookFields::updateAuthorChange,
                label = stringResource(Res.string.author),
                placeholder = stringResource(Res.string.author_placeholder)
            )

            CustomIntegerField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                value = state.bookFields.pages,
                onValueChange = state.bookFields::updatePagesChange,
                label = stringResource(Res.string.pages),
                placeholder = stringResource(Res.string.pages_placeholder)
            )
        }
    }
}