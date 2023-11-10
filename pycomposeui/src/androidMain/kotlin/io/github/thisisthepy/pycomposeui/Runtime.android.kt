package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import com.chaquo.python.PyObject


/*
 * Description: Composable Template contains Python codes can be invoked by Kotlin functions
 */
@JvmName("ComposableTemplate")
@Composable
fun ComposableTemplate(content: PyObject, vararg args: Any) {
    content.call(*args)
}
