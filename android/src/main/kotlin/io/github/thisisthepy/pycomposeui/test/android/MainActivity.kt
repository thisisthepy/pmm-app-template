package io.github.thisisthepy.pycomposeui.test.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.thisisthepy.pycomposeui.PythonAppView
import io.github.thisisthepy.pycomposeui.PythonLauncher
import io.github.thisisthepy.pycomposeui.moduleNamePreset
import io.github.thisisthepy.pycomposeui.test.common.App
import io.github.thisisthepy.pycomposeui.test.common.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                println(currentComposer)
                PythonLauncher {
                    try {
                        moduleNamePreset = "pycomposeui.main"
                    } catch (ignored: IllegalStateException) {}

                    PythonAppView(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    App()
}
