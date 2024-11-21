package com.bruno13palhano.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bruno13palhano.ui.navigation.Routes
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.app_name
import studiorum.composeapp.generated.resources.books_title
import studiorum.composeapp.generated.resources.home_title

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DrawerMenu(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val menuItems = listOf(
        Screen.Home,
        Screen.Books
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var selectedItem by remember { mutableStateOf(menuItems[0]) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerShape = RectangleShape) {
                LazyColumn {
                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            text = stringResource(Res.string.app_name)
                        )
                    }
                    items(items = menuItems) { item ->
                        val selected = currentDestination?.isRouteSelected(screen = item)

                        NavigationDrawerItem(
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                                .height(52.dp),
                            shape = RoundedCornerShape(0, 50, 50, 0),
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            label = { Text(stringResource(item.titleResource)) },
                            selected = selected == true,
                            onClick = {
                                selectedItem = item
                                navController.navigate(route = item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        },
        content = content
    )
}

internal fun <T: Routes>NavDestination.isRouteSelected(screen: Screen<T>): Boolean {
    return hierarchy.any {
        it.route?.split(".")?.lastOrNull() == screen.route.toString()
    }
}

internal sealed class Screen<T: Routes>(
    val route: T,
    val icon: ImageVector,
    val titleResource: StringResource
) {
    data object Home : Screen<Routes>(
        route = Routes.Home,
        icon = Icons.Filled.Home,
        titleResource = Res.string.home_title
    )

    data object Books : Screen<Routes>(
        route = Routes.Books,
        icon = Icons.AutoMirrored.Filled.List,
        titleResource = Res.string.books_title
    )
}