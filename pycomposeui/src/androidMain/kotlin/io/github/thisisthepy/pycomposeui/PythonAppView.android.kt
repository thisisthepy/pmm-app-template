package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.chaquo.python.PyObject
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
fun PythonWidget(moduleName: String, composableName: String, content: @Composable () -> Unit) {
    val py = Python.getInstance()
    val module = py.getModule(moduleName)
    module.callAttr(composableName, content)
}

fun runPy(moduleName: String, functionName: String): String {
    val py = Python.getInstance()
    val module = py.getModule(moduleName)
    val result = module.callAttr(functionName)
    return result.toString()
}

fun getVersion(): String {
    val py = Python.getInstance()
    val sys = py.getModule("sys")
    return sys.get("version").toString()
}

@Composable
fun PythonAppView(moduleName: String, composableName: String) {
    PythonWidget(moduleName, composableName) { }
}

@Composable
fun ComposableTemplate(pyFunction: PyObject) {
    pyFunction.callAttr("__call__")
}
