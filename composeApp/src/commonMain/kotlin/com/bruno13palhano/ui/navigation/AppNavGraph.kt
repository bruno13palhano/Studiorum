package com.bruno13palhano.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.bruno13palhano.ui.books.books.presenter.BooksRoute
import com.bruno13palhano.ui.home.presenter.HomeRoute
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onIconMenuClick: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Main
    ) {
        navigation<Routes.Main>(startDestination = Routes.Home) {
            composable<Routes.Home> {
                HomeRoute(modifier = modifier)
            }

            composable<Routes.Books> {
                BooksRoute(
                    modifier = modifier,
                    navigateToNewBook = {
                        navController.navigate(Routes.NewBook)
                    },
                    navigateToEditBook = {
                        navController.navigate(Routes.EditBook(id = it))
                    }
                )
            }

            composable<Routes.NewBook> {
                Text("New Book")
            }

            composable<Routes.EditBook> {
                val id = it.toRoute<Routes.EditBook>().id

                Text("Edit Book with id: $id")
            }
        }
    }
}

sealed interface Routes {
    @Serializable
    data object Main : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object Books : Routes

    @Serializable
    data object NewBook : Routes

    @Serializable
    data class EditBook(val id: Long) : Routes
}