import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import io.github.thisisthepy.pycompose.test.common.UIShow

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) {
            UIShow()
        }
    }
}
