package io.github.thisisthepy.pycomposeui.test.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.thisisthepy.pycomposeui.*
import io.github.thisisthepy.pycomposeui.test.common.App
import io.github.thisisthepy.pycomposeui.test.common.Greeting


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PythonLauncher {
                moduleNamePreset = "pycomposeui.main"

                Surface(Modifier.fillMaxSize()) {
                    Column(Modifier.fillMaxSize()) {
                        PythonWidget("UiTest", Modifier) {
                            Text(Greeting().greet()+"khlkhlkgfdgj")
                        }
                        PythonWidget("RichText", Modifier) {
                            Text(Greeting().greet()+"ssss")
                        }
                    }
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
