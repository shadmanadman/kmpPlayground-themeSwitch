package org.kmp.playground.theme.toggle

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ThemeToggle",
    ) {
        App()
    }
}