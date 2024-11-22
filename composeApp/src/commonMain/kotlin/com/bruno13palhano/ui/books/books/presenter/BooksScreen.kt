package com.bruno13palhano.ui.books.books.presenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bruno13palhano.model.Book
import com.bruno13palhano.ui.books.books.viewmodel.BooksViewModel
import com.bruno13palhano.ui.components.CircularProgress
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
    onIconMenuClick: () -> Unit,
    viewModel: BooksViewModel = koinViewModel<BooksViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = rememberFlowWithLifecycle(viewModel.sideEffect)

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is BooksSideEffect.NavigateToNewBook -> navigateToNewBook()

                is BooksSideEffect.NavigateToEditBook -> navigateToEditBook(effect.id)

                is BooksSideEffect.OpenDrawerMenu -> onIconMenuClick()
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
                    IconButton(onClick = { onAction(BooksAction.OnIconMenuClick) }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
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
        when (state) {
            is BooksState.Loading -> {
                CircularProgress(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            }

            is BooksState.Books -> {
                BookList(
                    paddingValues = it,
                    books = state.books,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun BookList(
    paddingValues: PaddingValues,
    books: List<Book>,
    onAction: (action: BooksAction) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(items = books, key = { book -> book.id }) { book ->
            ListItem(
                modifier = Modifier.clickable {
                    onAction(BooksAction.OnBookClick(book.id))
                },
                headlineContent = {
                    Column {
                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = book.author,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            )
        }
    }
}