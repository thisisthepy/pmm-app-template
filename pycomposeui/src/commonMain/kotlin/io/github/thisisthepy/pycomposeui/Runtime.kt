package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import kotlin.jvm.JvmName


@JvmName("ComposableWrapper")
@Composable
fun ComposableWrapper(content: @Composable (args: Array<Any>) -> Unit, args: Array<Any>) {
    content(args)
}
