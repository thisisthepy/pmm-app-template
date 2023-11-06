package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform


@Composable
fun PythonLauncher(
    content: @Composable () -> Unit
) {
    if (!Python.isStarted()) {
        Python.start(AndroidPlatform(LocalContext.current))
        println("PythonLauncher: Python is started...")
    }
    content()
}

@Composable
fun PythonAppView(moduleName: String, composableName: String) {
    val py = Python.getInstance()
    val module = py.getModule(moduleName)
    module.callAttr(composableName)
}
