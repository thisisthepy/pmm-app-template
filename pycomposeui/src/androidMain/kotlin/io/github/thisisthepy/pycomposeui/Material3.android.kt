package io.github.thisisthepy.pycomposeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chaquo.python.PyObject


@JvmName("SimpleColumnWidget")
@Composable
fun SimpleColumnWidget(
    content: PyObject
) {
    Column {
        content.call()
    }
}

@JvmName("SimpleRowWidget")
@Composable
fun SimpleRowWidget(
    content: PyObject
) {
    Row {
        content.call()
    }
}


@JvmName("SimpleButtonWidget")
@Composable
fun SimpleButtonWidget(
    onClick: PyObject,
    content: PyObject
) {
    Button({ onClick.call() }) {
        content.call()
    }
}
