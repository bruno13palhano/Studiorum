package com.bruno13palhano

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bruno13palhano.di.initKoin
import org.jetbrains.compose.resources.stringResource
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.app_name

fun main() = application {

    initKoin {}

    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        App()
    }
}