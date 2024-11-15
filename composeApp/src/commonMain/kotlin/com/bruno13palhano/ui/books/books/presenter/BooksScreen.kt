package com.bruno13palhano.ui.books.books.presenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bruno13palhano.ui.books.books.viewmodel.BooksViewModel
import com.bruno13palhano.ui.shared.rememberFlowWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.add_new_book
import studiorum.composeapp.generated.resources.books_title

@Composable
internal fun BooksRoute(
    modifier: Modifier = Modifier,
    navigateToNewBook: () -> Unit,
    navigateToEditBook: (id: Long) -> Unit,
    viewModel: BooksViewModel = koinViewModel<BooksViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is BooksSideEffect.Loading -> {

                }

                is BooksSideEffect.NavigateToNewBook -> navigateToNewBook()

                is BooksSideEffect.NavigateToEditBook -> navigateToEditBook(effect.id)
            }
        }
    }

    BooksContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BooksContent(
    modifier: Modifier = Modifier,
    state: BooksState,
    onAction: (action: BooksAction) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.books_title)) },
                navigationIcon = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(BooksAction.OnNewBookClick) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.add_new_book)
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = state.books, key = { book -> book.id }) { book ->
                ListItem(
                    modifier = Modifier.clickable {
                        onAction(BooksAction.OnBookClick(book.id))
                    },
                    headlineContent = {
                        Text(text = book.title)
                    }
                )
            }
        }
    }
}