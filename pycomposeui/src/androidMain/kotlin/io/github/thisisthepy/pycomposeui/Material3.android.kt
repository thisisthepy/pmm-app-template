package io.github.thisisthepy.pycomposeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chaquo.python.PyObject


@JvmName("SimpleColumnWidget")
@Composable
fun SimpleColumnWidget(
    content: PyObject
) {
    Column(Modifier.fillMaxSize()) {
        content.call()
    }
}
