package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import com.chaquo.python.PyObject


@JvmName("ComposableTemplate")
@Composable
fun ComposableTemplate(content: PyObject) {
    content.call()
}
