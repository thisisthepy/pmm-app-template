package io.github.thisisthepy.pycomposeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@JvmName("SimpleColumnWidget")
@Composable
fun SimpleColumnWidget(
    content: @Composable () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        content()
    }
}
