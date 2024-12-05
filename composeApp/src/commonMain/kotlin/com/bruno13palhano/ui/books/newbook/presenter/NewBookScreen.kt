package com.bruno13palhano.ui.books.newbook.presenter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.bruno13palhano.ui.books.newbook.viewmodel.NewBookViewModel
import com.bruno13palhano.ui.books.shared.BookContent
import com.bruno13palhano.ui.shared.clickableWithoutRipple
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.done
import studiorum.composeapp.generated.resources.fill_missing_fields
import studiorum.composeapp.generated.resources.navigate_back
import studiorum.composeapp.generated.resources.new_book_title

@Composable
internal fun NewBookRoute(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: NewBookViewModel = koinViewModel<NewBookViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(Res.string.fill_missing_fields)

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is NewBookSideEffect.InvalidField -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = errorMessage,
                            withDismissAction = true
                        )
                    }
                }

                is NewBookSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    NewBookContent(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewBookContent(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    state: NewBookState,
    onAction: (action: NewBookAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier.clickableWithoutRipple {
            keyboardController?.hide()
            focusManager.clearFocus()
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.new_book_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(NewBookAction.OnNavigateBackClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.navigate_back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(NewBookAction.OnDoneClick) }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(Res.string.done)
                )
            }
        }
    ) {
        BookContent(
            modifier = Modifier.padding(it),
            title = state.bookFields.title,
            author = state.bookFields.author,
            categories = state.bookFields.categories,
            category = state.bookFields.category,
            pages = state.bookFields.pages,
            wasRead = state.bookFields.wasRead,
            categoryVisible = state.categoryVisible,
            invalidField = state.invalidField,
            updateTitleChange = state.bookFields::updateTitleChange,
            updateAuthorChange = state.bookFields::updateAuthorChange,
            updateCategoryChange = state.bookFields::updateCategoryChange,
            addCategory = state.bookFields::addCategory,
            removeCategory = state.bookFields::removeCategory,
            updatePagesChange = state.bookFields::updatePagesChange,
            updateWasReadChange = state.bookFields::updateWasReadChange,
            updateCategoryVisibility = { visible ->
                onAction(NewBookAction.OnCategoryVisibilityClick(visible = visible))
            }
        )
    }
}