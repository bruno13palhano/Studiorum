package com.bruno13palhano

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bruno13palhano.ui.components.DrawerMenu
import com.bruno13palhano.ui.navigation.MainNavGraph
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DrawerMenu(
                drawerState = drawerState,
                navController = navController
            ) {
                val scope = rememberCoroutineScope()

                Scaffold(
                    bottomBar = {

                    }
                ) {
                    MainNavGraph(
                        modifier = Modifier.padding(it),
                        navController = navController
                    ) {
                        scope.launch { drawerState.open() }
                    }
                }
            }
        }
    }
}