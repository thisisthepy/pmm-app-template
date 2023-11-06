package io.github.thisisthepy.pycomposeui.test.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.thisisthepy.pycomposeui.PythonLauncher
import io.github.thisisthepy.pycomposeui.PythonAppView
import io.github.thisisthepy.pycomposeui.getVersion
import io.github.thisisthepy.pycomposeui.test.common.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PythonLauncher {
                Text(getVersion())
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    App()
}
