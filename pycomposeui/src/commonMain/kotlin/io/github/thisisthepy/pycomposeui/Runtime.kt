package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import kotlin.jvm.JvmName


@JvmName("ComposableWrapper")
@Composable
fun ComposableWrapper(content: @Composable () -> Unit) {
    content()
}
