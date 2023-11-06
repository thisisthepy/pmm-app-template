import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady
import io.github.thisisthepy.pycomposeui.test.common.App

fun main() {
    onWasmReady {
        Window("PyComposeTest") {
            App()
        }
    }
}
