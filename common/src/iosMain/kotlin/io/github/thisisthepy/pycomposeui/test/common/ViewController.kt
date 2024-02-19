package io.github.thisisthepy.pycomposeui.test.common

import androidx.compose.ui.window.ComposeUIViewController
import io.github.thisisthepy.python.Python
import platform.UIKit.UIViewController


fun MainViewController(
    pyInitialize: () -> Unit = { },
    pyFinalize: () -> Unit = { },
    pyImport: (String) -> Long = { _ -> 0 },
    pyObjectGetAttrString: (Long, String) -> Long = { _, _ -> 0 },
    pyObjectCallObject: (Long, Long) -> Long = { _, _ -> 0 }
): UIViewController = ComposeUIViewController {
    Python.start(
        Python.PyFFI(pyInitialize, pyFinalize, pyImport, pyObjectGetAttrString, pyObjectCallObject)
    )
    App()
}
