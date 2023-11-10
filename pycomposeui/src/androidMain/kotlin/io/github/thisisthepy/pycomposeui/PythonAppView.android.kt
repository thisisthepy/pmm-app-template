package io.github.thisisthepy.pycomposeui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform


@Composable
fun PythonLauncher(
    content: @Composable () -> Unit
) {
    if (!Python.isStarted()) {
        val platform = AndroidPlatform(LocalContext.current)
        platform.redirectStdioToLogcat()
        Python.start(platform)
        println("PythonLauncher: Python is started...")
        val runtime = Python.getInstance().getModule("pycomposeui.runtime")
        runtime.get("Composable")?.callAttr("register_composer", currentComposer)
    }
    content()
}

@Composable
fun PythonWidget(moduleName: String, composableName: String, content: @Composable () -> Unit) {
    val py = Python.getInstance()
    val module = py.getModule(moduleName)
    module.callAttr(composableName, content)
}

@Composable
fun PythonWidget(moduleName: String, composableName: String) {
    PythonWidget(moduleName, composableName) {}
}

@Composable
fun PythonSimpleWidget(moduleName: String, composableName: String) {
    val py = Python.getInstance()
    val module = py.getModule(moduleName)
    module.callAttr(composableName)
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

fun getText(): String {
    val py = Python.getInstance()
    val sys = py.getModule("pycomposeui")
    return sys.get("version").toString()
}

@Composable
fun PythonAppView(moduleName: String, composableName: String) {
    PythonWidget(moduleName, composableName)
}

@Composable
fun BoxBinding(modifier: Modifier = Modifier,
               contentAlignment: Alignment = Alignment.TopStart,
               propagateMinConstraints: Boolean = false,
               content: @Composable BoxScope.() -> Unit) {
    Box(modifier, contentAlignment, propagateMinConstraints, content)
}

fun getModifier() = Modifier

fun getAlignment() = Alignment

