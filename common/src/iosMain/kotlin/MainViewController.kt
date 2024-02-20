import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import io.github.thisisthepy.pycomposeui.test.common.AppTheme
import io.github.thisisthepy.python.Python
import platform.UIKit.UIViewController
import kotlin.experimental.ExperimentalObjCName


@OptIn(ExperimentalObjCName::class)
@ObjCName("MainViewController")
class MainViewController(
    pyInitialize: () -> Unit = { },
    pyFinalize: () -> Unit = { },
    pyImport: (String) -> Long = { _ -> 0 },
    pyErrorOccurred: () -> Boolean = { false },
    pyErrorPrint: () -> Unit = { },
    pyObjectGetAttrString: (Long, String) -> Long = { _, _ -> 0 },
    pyObjectCallObject: (Long, Long) -> Long = { _, _ -> 0 }
) {
    init {
        Python.start(Python.PyFFI(pyInitialize, pyFinalize, pyImport, pyErrorOccurred, pyErrorPrint,
            pyObjectGetAttrString, pyObjectCallObject))
    }

    val rootView: UIViewController
        get() = ComposeUIViewController {
            PythonApp()
        }

    fun onDestroy() {
        Python.kill()
    }
}


@Composable
fun PythonApp() {
    AppTheme {
        println(currentComposer)
        PythonLauncher {
            PythonAppView(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun PythonLauncher(
    content: @Composable () -> Unit
) {
//    val binding = Python.import("pycomposeui.binding")
//    val runtime = Python.import("pycomposeui.runtime")
//    val composable = runtime.getAttribute("Composable")
//    val registerComposer = composable!!.getAttribute("register_composer")
//    if (registerComposer != null) {
//        registerComposer.call(currentComposer)
//        println("PythonLauncher: Composer is registered.")
//    } else {
//        throw RuntimeException("PythonLauncher: Failed to register Composer. Cannot find Composable class in python runtime.")
//    }

    content()
}

@Composable
fun PythonAppView(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null
) {
    val module = Python.import("main")
    //val app = module.getAttribute("App")
    Surface(modifier, shape, color, contentColor, tonalElevation, shadowElevation, border) {
        //app!!.call()
    }
}
