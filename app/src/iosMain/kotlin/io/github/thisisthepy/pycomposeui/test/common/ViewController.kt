package io.github.thisisthepy.pycomposeui.test.common

import App
import androidx.compose.ui.window.ComposeUIViewController
import device.UIKit.UIViewController


fun MainViewController(): UIViewController = ComposeUIViewController {
    App()
}
